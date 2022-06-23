package com.onix.internship.ui.moreinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MoreInfoFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreInfoFragment : BaseFragment<MoreInfoFragmentBinding>(R.layout.more_info_fragment) {

    override val viewModel: MoreInfoViewModel by viewModel()
    private val args : MoreInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val scanResult = args.scanResult
        binding.tvInfo.text = scanResult.toString().replace(",", "\n")

        return view
    }

}