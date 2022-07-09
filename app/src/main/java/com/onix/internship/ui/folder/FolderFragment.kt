package com.onix.internship.ui.folder

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FolderFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FolderFragment : BaseFragment<FolderFragmentBinding>(R.layout.folder_fragment) {

    override val viewModel: FolderViewModel by viewModel()

}