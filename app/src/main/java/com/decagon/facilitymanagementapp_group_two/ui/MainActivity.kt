package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navCon = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navCon)

        navCon.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.onboardingFragment) {

                binding.bottomNavigation.visibility = View.VISIBLE
            } else{
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

    }





}
