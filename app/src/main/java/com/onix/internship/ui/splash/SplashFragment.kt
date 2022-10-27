package com.onix.internship.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.SplashFragmentBinding
import com.onix.internship.ui.popup.PopupClickListener
import com.onix.internship.ui.popup.PopupWindowCreator
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : BaseFragment<SplashFragmentBinding>(R.layout.splash_fragment) {

    override val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis.",
            "Some text",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis.\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis.\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis.\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis.",
            "Some text",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris enim est, tincidunt maximus sapien ut, pulvinar lobortis leo. Phasellus faucibus accumsan tempus. Phasellus nec molestie augue. Aenean vestibulum leo et mauris efficitur, ac posuere est imperdiet. Maecenas pulvinar id mi vitae porta. Fusce condimentum orci mi, vitae rhoncus nibh malesuada ut. Aliquam imperdiet libero et pharetra elementum. In eget vehicula purus. Nam eget ullamcorper mi, quis ultrices justo. Donec lectus arcu, pretium vitae vehicula at, venenatis id ligula. Aliquam rutrum, neque eu vehicula egestas, est arcu scelerisque nibh, sit amet facilisis nisi dolor tristique turpis."
        )

        val adapter = SplashAdapter(
            itemClick = {
                PopupWindowCreator().showPopup(
                    context = requireContext(),
                    view = it,
                    parent = binding.root,
                    menuRes = R.menu.menu,
                    clickListener = onMenuClickListener
                )
            }
        )

        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            this.adapter = adapter
        }

        adapter.submitList(list)
    }

    private val onMenuClickListener = object : PopupClickListener {
        override fun onItemClickListener(itemId: Int, popup: PopupWindow) {
            when (itemId) {
                R.id.deleteMessageItem -> {
                    showSnack("Delete")
                }
                R.id.copyMessageItem -> {
                    showSnack("Copy")
                }
                R.id.editMessageItem -> {
                    showSnack("Edit")
                }
            }
            popup.dismiss()
        }
    }
}