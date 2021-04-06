package com.decagon.facilitymanagementapp_group_two.utils

import android.content.SharedPreferences

fun writeSsoDetailsToSharedPref(firstName: String, lastName: String, email: String, sharedPreferences: SharedPreferences) {
    sharedPreferences.edit().putString("firstName", firstName).apply()
    sharedPreferences.edit().putString("lastName", lastName).apply()
    sharedPreferences.edit().putString("email", email).apply()
}
