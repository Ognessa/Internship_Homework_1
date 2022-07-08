package com.onix.internship.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.onix.internship.R
import com.onix.internship.arch.BaseActivity
import com.onix.internship.databinding.ActivityMainBinding
import com.onix.internship.objects.MusicPlayer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreen : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModel()
    val player : MusicPlayer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player.initPlayer()
    }

    override fun onPause() {
        player.stopMusic()
        super.onPause()
    }

    //disable back press
    override fun onBackPressed() {}

    override val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeHostNavFragment) as NavHostFragment
        navHostFragment.navController
    }

}