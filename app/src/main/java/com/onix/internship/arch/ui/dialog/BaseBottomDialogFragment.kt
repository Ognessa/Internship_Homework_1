package com.onix.internship.arch.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onix.internship.R
import com.onix.internship.arch.component.error.ErrorHandler
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ktx.getString
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

abstract class BaseBottomDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val contentLayoutID: Int,
) : BottomSheetDialogFragment(), BaseView, RouterProvider {

    protected lateinit var binding: T
        private set

    protected val logger: Logger by inject { parametersOf(this) }
    private val errorHandler: ErrorHandler by inject { parametersOf(requireContext()) }

    protected open val expandToScreenHeight: Boolean = false

    override val router: Router by lazy {
        NavComponentRouter(
            navController = findNavController(),
            logger = logger
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setWindowProperties()
        binding = DataBindingUtil.inflate(inflater, contentLayoutID, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (expandToScreenHeight) {
            dialog.setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                setBottomSheetFullscreen(bottomSheetDialog)
            }
        }
        return dialog
    }

    private fun setWindowProperties() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setBaseObservers()
        setObservers()
    }

    protected fun navigate(command: Command) {
        when (command) {
            is NavDirection -> router.navigate(command)
            Command.Back -> router.popBackStack()
            Command.Root -> router.popToRoot()
            Command.Close -> requireActivity().finish()
            is Command.FeatureCommand -> navigateByFeature(command)
            is Command.Route -> commandError(command)
        }
    }

    protected open fun setBaseObservers() {
        getViewModel()?.let {
            it.errorEvent.observe(viewLifecycleOwner, ::displayError)
            it.navigationCommand.observe(viewLifecycleOwner, ::navigate)
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

    protected open fun setObservers() {}

    protected open fun getViewModel(): BaseViewModel? {
        return null
    }

    private fun setBottomSheetFullscreen(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let { dialog ->
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(dialog)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun showMessage(textProvider: TextProvider) {
        context?.let { context ->
            Toast.makeText(
                context.applicationContext,
                textProvider.getString(context),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun displayError(failure: Failure) {
        errorHandler.parseFailure(failure)
    }
}