package com.decagon.facilitymanagementapp_group_two.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import com.decagon.facilitymanagementapp_group_two.R

fun setStatusBarBaseColor(activity : Activity, context : Context){
    /**
     * This sets the status bar to grey for the single complaint fragment if version code greater
     * than or equal marshmallow else maintains the default status bar color
     */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = context.getColor(R.color.grey_100)
    }
}