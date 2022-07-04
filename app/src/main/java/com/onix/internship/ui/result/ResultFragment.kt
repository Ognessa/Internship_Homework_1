package com.onix.internship.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentResultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {

    override val viewModel: ResultViewModel by viewModel()
    val args : ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val adapter = ResultRVAdapter()
        adapter.setContent(args.value.asList())

        binding.tvWord.text = args.key
        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter

        return view
    }

}