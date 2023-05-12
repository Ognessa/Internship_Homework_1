package com.onix.internship.ui.splash

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.SnapHelper
import com.onix.internship.R
import com.onix.internship.arch.controller.BaseViewModel
import com.onix.internship.arch.ui.fragment.BaseFragment
import com.onix.internship.databinding.SplashFragmentBinding
import com.onix.internship.ui.splash.adapter.ImageCarrouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.max
import kotlin.math.min

class SplashFragment : BaseFragment<SplashFragmentBinding>(R.layout.splash_fragment) {

    private val viewModel: SplashViewModel by viewModel()

    private val snapHelper = LinearSnapHelper()

    private val scrollListener by lazy {
        ScrollHelper(
            snapHelper = snapHelper,
            presenter = viewModel
        )
    }

    private val imageAdapter by lazy {
        ImageCarrouselAdapter(
            viewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.images.observe(viewLifecycleOwner, ::handleAdapter)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        calculateListPadding()
    }

    private fun setupAdapter() {
        binding.recycler.adapter = imageAdapter
        calculateListPadding()
        snapHelper.attachToRecyclerView(binding.recycler)
        binding.recycler.addOnScrollListener(scrollListener)
    }

    private fun handleAdapter(list: List<String>) {
        imageAdapter.items = list
    }

    private fun calculateListPadding() {
        val paddingRL: Int =
            (resources.displayMetrics.widthPixels - resources.getDimensionPixelSize(R.dimen.width_150)) / 2
        binding.recycler.setPadding(paddingRL, 0, paddingRL, 0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}

class ScrollHelper(
    private val snapHelper: LinearSnapHelper,
    private val presenter: ScrollPresenter
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.layoutManager?.let { presenter.onItemSelected(snapHelper.getSnapPosition(it)) }
        }
    }
}

fun SnapHelper.getSnapPosition(layoutManager: LayoutManager): Int {
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

//class CenterSnapHelper(
//    private val presenter: ScrollPresenter
//) : LinearSnapHelper() {
//    override fun findTargetSnapPosition(
//        layoutManager: RecyclerView.LayoutManager?,
//        velocityX: Int,
//        velocityY: Int
//    ): Int {
//        val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
//
//        if (layoutManager != null) {
//            val position = layoutManager.getPosition(centerView)
//            presenter.onItemSelected(position)
//            return position
//        } else
//            return RecyclerView.NO_POSITION
//    }
//}