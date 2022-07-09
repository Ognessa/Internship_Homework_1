package com.onix.internship.ui.tabMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.TabMenuFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabMenuFragment : BaseFragment<TabMenuFragmentBinding>(R.layout.tab_menu_fragment) {

    override val viewModel: TabMenuViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val navHost = childFragmentManager.findFragmentById(R.id.tabNavFragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        return view
    }

}