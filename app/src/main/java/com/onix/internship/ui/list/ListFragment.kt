package com.onix.internship.ui.list

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    override val viewModel: ListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RVAdapter()
        binding.rv.adapter = adapter

        adapter.submitList(
            listOf(
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three",
                "One", "Two", "Three"
            )
        )

    }

}