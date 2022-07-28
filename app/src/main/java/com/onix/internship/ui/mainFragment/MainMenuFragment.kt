package com.onix.internship.ui.mainFragment

import android.os.Bundle
import android.view.View
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
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.MainMenuFragmentBinding
import com.onix.internship.objects.AddNoteFragmentStates
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : BaseFragment<MainMenuFragmentBinding>(R.layout.main_menu_fragment) {

    override val viewModel: MainMenuViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val navController =
            (childFragmentManager.findFragmentById(R.id.hostNavFragment) as NavHostFragment).navController

        setupDrawerLayout(binding.drawerLayout, navController, binding.navigation, binding.toolbar)
        setupToolbar(binding.toolbar, binding.drawerLayout)
    }

    override fun setObservers() {
        viewModel.navigateToAddNote.observe(this) {
            navigate(
                MainMenuFragmentDirections.actionMainMenuFragmentToAddNoteFragment(
                    AddNoteFragmentStates.ADD_NEW_NOTE, 0
                )
            )
        }
    }

    private fun setupDrawerLayout(
        drawerLayout: DrawerLayout,
        navController: NavController,
        navigation: NavigationView,
        toolbar: Toolbar
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
                    toolbar.setTitle(R.string.notes_list_menu)
                    viewModel.setAddNoteBtnVisible(true)
                    true
                }
                R.id.emergencyFragment -> {
                    navController.navigate(R.id.emergencyFragment)
                    toolbar.setTitle(R.string.emergency_menu)
                    viewModel.setAddNoteBtnVisible(false)
                    true
                }
                R.id.helpFragment -> {
                    navController.navigate(R.id.helpFragment)
                    toolbar.setTitle(R.string.help_menu)
                    viewModel.setAddNoteBtnVisible(false)
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
        toolbar.setNavigationOnClickListener { drawerLayout.open() }
    }

}