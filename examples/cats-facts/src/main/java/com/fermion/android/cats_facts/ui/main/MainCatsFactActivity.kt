package com.fermion.android.cats_facts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.fermion.android.base.view.BaseActivity
import com.fermion.android.cats_facts.R
import com.fermion.android.cats_facts.databinding.ActivityMainCatsFactBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainCatsFactActivity :
    BaseActivity<ActivityMainCatsFactBinding, MainViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    override fun onStart() {
        super.onStart()

    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainCatsFactBinding
        get() = ActivityMainCatsFactBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)


    }

    override fun obtainViewModel(): Lazy<MainViewModel> {
        return viewModels()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}