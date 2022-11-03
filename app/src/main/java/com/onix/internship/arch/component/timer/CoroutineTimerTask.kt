package com.onix.internship.arch.component.timer

import com.onix.internship.BuildConfig
import com.onix.internship.arch.component.logger.AndroidLogger
import com.onix.internship.arch.component.logger.configuration.DefaultLoggerConfiguration
import com.onix.internship.arch.coroutine.CoroutineLauncher
import com.onix.internship.arch.coroutine.impl.DefaultCoroutineLauncher
import com.onix.internship.domain.component.logger.Level
import com.onix.internship.domain.component.logger.Logger
import com.onix.internship.domain.component.timer.TimerTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CoroutineTimerTask internal constructor(
    private val delay: Duration = Duration.ZERO,
    private val repeat: Duration? = null,
    private val launcher: CoroutineLauncher = DefaultCoroutineLauncher(GlobalScope),
    action: suspend () -> Unit
) : TimerTask {

    private val keepRunning = AtomicBoolean(true)
    private var job: Job? = null
    private val logger: Logger by lazy {
        AndroidLogger(
            configuration = DefaultLoggerConfiguration(
                this,
                isEnabled = BuildConfig.DEBUG
            )
        )
    }

    private val tryAction = suspend {
        try {
            action()
        } catch (e: Throwable) {
            logger.log(
                level = Level.Warning,
                message = "timer action failed: $action",
                throwable = e
            )
        }
    }

    override fun start() {
        job = launcher.launch {
            delay(delay)
            if (repeat != null) {
                while (keepRunning.get()) {
                    tryAction()
                    delay(repeat)
                }
            } else {
                if (keepRunning.get()) {
                    tryAction()
                }
            }
        }
    }

    /**
     * Initiates an orderly shutdown, where if the timer task is currently running,
     * we will let it finish, but not run it again.
     * Invocation has no additional effect if already shut down.
     */
    fun shutdown() {
        keepRunning.set(false)
    }

    /**
     * Immediately stops the timer task, even if the job is currently running,
     * by cancelling the underlying Coroutine Job.
     */
    override fun stop() {
        shutdown()
        job?.cancel("cancel() called")
    }

    companion object {
        /**
         * Runs the given `action` after the given `delay`,
         * once the `action` completes, waits the `repeat` duration
         * and runs again, until `shutdown` is called.
         *
         * if action() throws an exception, it will be swallowed and a warning will be logged.
         */
        fun start(
            delay: Duration = Duration.ZERO,
            repeat: Duration? = null,
            launcher: CoroutineLauncher = DefaultCoroutineLauncher(GlobalScope),
            action: suspend () -> Unit
        ): CoroutineTimerTask =
            CoroutineTimerTask(delay, repeat, launcher, action).also { it.start() }
    }

}
