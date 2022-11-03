package com.onix.internship.arch.coroutine.impl

import com.onix.internship.arch.coroutine.CoroutineLauncher
import com.onix.internship.domain.entities.failure.Failure
import kotlinx.coroutines.*

class DefaultCoroutineLauncher(
    private val scope: CoroutineScope,
    handleFailure: ((Failure) -> Unit)? = null
) : CoroutineLauncher {

    override fun launch(supervisor: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(errorHandler) {
            if (supervisor) {
                supervisorScope {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            handleFailure?.invoke(Failure.CoroutineException(exception))
        }
    }
}