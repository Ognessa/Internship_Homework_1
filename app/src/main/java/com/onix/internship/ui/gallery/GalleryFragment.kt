package com.onix.internship.ui.gallery

import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.SplashFragmentBinding
import com.onix.internship.ui.gallery.adapter.GalleryAdapter
import com.onix.internship.ui.gallery.adapter.model.GalleryAdapterModel
import com.onix.internship.ui.gallery.adapter.model.GalleryMapper
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO ask read storage permission
class GalleryFragment : BaseFragment<SplashFragmentBinding>(R.layout.splash_fragment) {

    private val viewModel: GalleryViewModel by viewModel()

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(
            presenter = viewModel
        )
    }

    private val mapper: GalleryMapper.UrlToGalleryModel by lazy {
        GalleryMapper.UrlToGalleryModel(
            requireActivity()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupAdapter()
        loadImages()
    }

    private fun loadImages() {
        val list = getAllShownImagesPath()
        if (list.isNotEmpty()) {
            galleryAdapter.items = list
            binding.gallery.visibility = View.VISIBLE
            binding.emptyText.visibility = View.INVISIBLE
        } else {
            binding.gallery.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    private fun getAllShownImagesPath(): List<GalleryAdapterModel> {
        val columns = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
        )
        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
        val queryUri = MediaStore.Files.getContentUri("external")

        val cursor = requireActivity().contentResolver.query(
            queryUri,
            columns,
            selection,
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        )

        val files = mutableListOf<String>()

        if (cursor != null) {
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)
                files.add(absolutePathOfImage)
            }
        }

        return mapper.map(files)
    }

    private fun setupAdapter() {
        binding.gallery.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = galleryAdapter
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel
}