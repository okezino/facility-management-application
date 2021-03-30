package com.decagon.facilitymanagementapp_group_two

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * This notifies dagger to generate the necessary classes needed to handle the actual injection of
 * members into the android class
 */
@HiltAndroidApp
class FacilityManagementApplication : Application()
