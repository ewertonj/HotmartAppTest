package com.ewerton.hotmartapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.ewerton.hotmartapplication.databinding.ActivityMainBinding
import com.ewerton.hotmartapplication.platform.setupAppearence

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        navController = this.findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.nav_graph)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.locationsFragment-> {
                    binding.toolbar.setupAppearence(View.VISIBLE)
                }
                else -> {
                    binding.toolbar.setupAppearence(View.GONE)
                }
            }
        }
    }
}