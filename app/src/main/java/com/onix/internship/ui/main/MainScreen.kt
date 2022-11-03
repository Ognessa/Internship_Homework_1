package com.onix.internship.ui.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.onix.internship.R
import com.onix.internship.arch.ui.activity.BaseActivity
import com.onix.internship.databinding.ActivityMainBinding

class MainScreen : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeHostNavFragment) as NavHostFragment
        navHostFragment.navController
    }
}