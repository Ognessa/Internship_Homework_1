package com.onix.internship.arch.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.onix.internship.arch.component.error.ErrorHandler
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.coroutine.impl.DefaultCoroutineLauncher
import com.onix.internship.arch.ktx.getString
import com.onix.internship.arch.provider.CoroutineProvider
import com.onix.internship.arch.provider.RouterProvider
import com.onix.internship.arch.router.NavComponentRouter
import com.onix.internship.arch.router.Router
import com.onix.internship.arch.router.command.Command
import com.onix.internship.arch.router.command.NavDirection
import com.onix.internship.arch.ui.view.BaseView
import com.onix.internship.arch.ui.view.model.TextProvider
import com.onix.internship.domain.component.logger.Level
import com.onix.internship.domain.component.logger.Logger
import com.onix.internship.domain.entities.failure.Failure
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutID: Int
) : AppCompatActivity(), BaseView, RouterProvider, CoroutineProvider {

    protected lateinit var binding: T
        private set

    protected abstract val navController: NavController

    private val logger: Logger by inject { parametersOf(this) }

    protected open val failureHandler: ErrorHandler by inject()

    override val router: Router by lazy {
        NavComponentRouter(
            navController = navController,
            logger = logger
        )
    }

    override val launcher by lazy { DefaultCoroutineLauncher(lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayoutID)
        binding.lifecycleOwner = this
        initBaseObservers()
        setObservers()
    }

    protected open fun initBaseObservers() {
        getViewModel()?.let {
            it.errorEvent.observe(this, ::displayError)
            it.navigationCommand.observe(this, ::navigate)
            it.message.observe(this, ::showMessage)
        }
    }

    protected open fun setObservers() {}

    protected open fun navigate(command: Command) {
        when (command) {
            is NavDirection -> router.navigate(command)
            Command.Back -> router.popBackStack()
            Command.Root -> router.popToRoot()
            Command.Close -> finish()
            is Command.FeatureCommand -> navigateByFeature(command)
            is Command.Route -> commandError(command)
        }
    }

    protected open fun navigateByFeature(command: Command.FeatureCommand) {
        logger.log(
            level = Level.Warning,
            message = "method navigateByFeature isn't implement for $command"
        )
    }

    private fun commandError(command: Command) {
        logger.log(
            level = Level.Warning,
            message = "navigation isn't implement for $command"
        )
    }

    override fun displayError(failure: Failure) {
        failureHandler.parseFailure(failure)
    }

    override fun showMessage(textProvider: TextProvider) {
        Toast.makeText(applicationContext, textProvider.getString(this), Toast.LENGTH_SHORT).show()
    }

    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

}
