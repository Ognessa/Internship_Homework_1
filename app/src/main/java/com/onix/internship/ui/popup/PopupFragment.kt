package com.onix.internship.ui.popup

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.MenuRes
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.FragmentPopupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopupFragment : BaseFragment<FragmentPopupBinding>(R.layout.fragment_popup) {
    override val viewModel: PopupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RVAdapter()
        binding.rv.adapter = adapter

        adapter.setContent(listOf(
            "One", "Two", "Three",
            "One", "Two", "Three",
            "One", "Two", "Three",
            "One", "Two", "Three",
            "One", "Two", "Three",
            "One", "Two", "Three"))
    }
}