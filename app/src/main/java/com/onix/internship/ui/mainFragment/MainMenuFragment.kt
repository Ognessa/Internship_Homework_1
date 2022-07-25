package com.onix.internship.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.MainMenuFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainMenuFragment : BaseFragment<MainMenuFragmentBinding>(R.layout.main_menu_fragment) {

    override val viewModel: MainMenuViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val navController =
            (childFragmentManager.findFragmentById(R.id.hostNavFragment) as NavHostFragment).navController

        setupDrawerLayout(binding.drawerLayout, navController, binding.navigation)
        setupToolbar(binding.toolbar, binding.drawerLayout)

        return view
    }

    private fun setupDrawerLayout(
        drawerLayout: DrawerLayout,
        navController: NavController,
        navigation: NavigationView
    ) {
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        navigation.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.notesListFragment -> {
                    navController.navigate(R.id.notesListFragment)
                    true
                }
                R.id.emergencyFragment -> {
                    navController.navigate(R.id.emergencyFragment)
                    true
                }
                R.id.helpFragment -> {
                    navController.navigate(R.id.helpFragment)
                    true
                }
                else -> {
                    false
                }

            }
        }
    }

    private fun setupToolbar(toolbar: Toolbar, drawerLayout: DrawerLayout) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener { drawerLayout.open() }
    }

}