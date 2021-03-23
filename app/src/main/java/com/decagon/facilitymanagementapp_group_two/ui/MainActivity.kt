package com.decagon.facilitymanagementapp_group_two.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController


import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.databinding.ActivityMainBinding
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        /**
         * Initialise the Microsoft ISingleAccountPublicClientApplication
         * interface which will be used in signing-in users
         */
        MsWebAuthentication.initialiseSingleAccount(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.dashboardFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.profileFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.feedsFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                else -> binding.bottomNavigation.visibility = View.GONE

            }
        }
    }

//=======
//import androidx.navigation.NavController
//import androidx.navigation.fragment.NavHostFragment
//import com.decagon.facilitymanagementapp_group_two.R
//
//
//class MainActivity : AppCompatActivity() {
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as
//            NavHostFragment
//
//
//
//>>>>>>> develop
 //   }



    }





