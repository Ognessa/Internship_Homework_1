package com.onix.internship.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.SplashFragmentBinding
import com.onix.internship.ui.gallery.adapter.GalleryAdapter
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO ask read storage permission
class GalleryFragment : BaseFragment<SplashFragmentBinding>(R.layout.splash_fragment) {

    private val viewModel: GalleryViewModel by viewModel()

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(
            adapterPresenter = viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = viewModel
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupAdapter()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseAudioPLaying()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.audioList.observe(viewLifecycleOwner, ::handleListUpdates)
    }

    private fun setupAdapter() {
        binding.gallery.adapter = galleryAdapter
    }

    private fun handleListUpdates(list: List<GalleryAdapterModel>) {
        if (list.isNotEmpty()) {
            galleryAdapter.items = list
            binding.gallery.visibility = View.VISIBLE
            binding.emptyText.visibility = View.INVISIBLE
        } else {
            binding.gallery.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}