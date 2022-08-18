package com.onix.internship.ui.tabMenu

import android.os.Bundle
import android.view.View
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.TabMenuFragmentBinding
import com.onix.internship.ui.advancedSearch.AdvancedSearchFragment
import com.onix.internship.ui.simpleSearch.SimpleSearchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabMenuFragment : BaseFragment<TabMenuFragmentBinding>(R.layout.tab_menu_fragment) {

    override val viewModel: TabMenuViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(SimpleSearchFragment(), resources.getString(R.string.simple_search_fr))
        adapter.addFragment(AdvancedSearchFragment(), resources.getString(R.string.advanced_search_fr))

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

    }

    override fun setObservers() {
        viewModel.searchModel.simpleSearch.observe(this){
            viewModel.simpleSearch(it)
        }

        viewModel.searchModel.advancedSearch.observe(this){
            viewModel.advancedSearch(it)
        }

        viewModel.searchModel.navigateToSoundsList.observe(this){
            navigate(TabMenuFragmentDirections.actionTabMenuFragmentToSoundListFragment())
        }
    }
}