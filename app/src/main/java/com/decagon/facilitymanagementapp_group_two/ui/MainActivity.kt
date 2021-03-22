package com.decagon.facilitymanagementapp_group_two.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.ms_auth.MsWebAuthentication

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as
                NavHostFragment
        navController = navHostFragment.navController

        /**
         * Initialise the Microsoft ISingleAccountPublicClientApplication
         * interface which will be used in signing-in users
         */
        MsWebAuthentication.initialiseSingleAccount(this, navController)

    }
}
