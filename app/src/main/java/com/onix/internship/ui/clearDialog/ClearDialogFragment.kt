package com.onix.internship.ui.clearDialog

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseDialog
import com.onix.internship.arch.ext.navigateToPrevious
import com.onix.internship.databinding.ClearDialogFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClearDialogFragment : BaseDialog<ClearDialogFragmentBinding>(R.layout.clear_dialog_fragment) {
    override val viewModel: ClearDialogViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun setObservers() {
        viewModel.moveBack.observe(this){
            navigateToPrevious()
        }
    }

}