package com.decagon.facilitymanagementapp_group_two.utils

import android.content.ContentResolver
import android.content.SharedPreferences
import android.net.Uri
import android.provider.OpenableColumns

fun writeSsoDetailsToSharedPref(firstName: String, lastName: String, email: String, sharedPreferences: SharedPreferences) {
    sharedPreferences.edit().putString("firstName", firstName).apply()
    sharedPreferences.edit().putString("lastName", lastName).apply()
    sharedPreferences.edit().putString("email", email).apply()
}

// Extension function
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}
