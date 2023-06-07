package com.onix.internship.ui.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.arch.ui.view.ktx.loadImage
import com.onix.internship.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {

    private val navArgs: ResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageResult.loadImage(navArgs.url)
    }

}