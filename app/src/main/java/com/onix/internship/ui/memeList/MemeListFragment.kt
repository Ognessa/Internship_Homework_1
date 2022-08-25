package com.onix.internship.ui.memeList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MemeListFragmentBinding
import com.onix.internship.ui.memeList.adapter.MemeAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemeListFragment : BaseFragment<MemeListFragmentBinding>(R.layout.meme_list_fragment){

    override val viewModel: MemeListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = viewModel.items
        val adapter = MemeAdapter()
        binding.memeList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                items.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}