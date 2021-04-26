package com.decagon.facilitymanagementapp_group_two.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.SharedPreferences
import android.net.Uri
import android.provider.OpenableColumns
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.decagon.facilitymanagementapp_group_two.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun writeSsoDetailsToSharedPref(
    firstName: String,
    lastName: String,
    email: String,
    squad: String,
    stack: String,
    mobile: String,
    sharedPreferences: SharedPreferences
) {
    sharedPreferences.edit().putString("firstName", firstName).apply()
    sharedPreferences.edit().putString("lastName", lastName).apply()
    sharedPreferences.edit().putString("email", email).apply()
    sharedPreferences.edit().putString("squad", squad).apply()
    sharedPreferences.edit().putString("stack", stack).apply()
    sharedPreferences.edit().putString("mobile", mobile).apply()
}

// Extension function on ContentResolver
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}

/**
 * An extension function for loading images from server using glide
 * with provisions for loading and error state
 */
fun ImageView.loadImage(imageUrl: String?) {
    val imgUri = imageUrl?.toUri()
    Glide.with(this)
        .load(imgUri).apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        ).into(this)
}

// Extension function for showing snack bar
fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

/**
 * Function to convert time to time ago. To be monitored...
 */
@SuppressLint("SimpleDateFormat")
 fun timeConvert(string: String?): String {
     val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
     sdf.timeZone = TimeZone.getTimeZone("GMT")
     return try {
         val time = sdf.parse(string).time
         val now = System.currentTimeMillis()
         val ago =
         DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
         ago.toString()
     } catch (e: ParseException) {
         e.printStackTrace()
         System.currentTimeMillis().toString()
     }
 }
