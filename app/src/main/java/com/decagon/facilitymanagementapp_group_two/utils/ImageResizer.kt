package com.decagon.facilitymanagementapp_group_two.utils

import android.graphics.Bitmap
import kotlin.math.round
import kotlin.math.sqrt

fun bitmapReducer(bitmap: Bitmap, MAX_SIZE : Int): Bitmap{
    var bitmapHeight = bitmap.height
    var bitmapWidth = bitmap.width

    var ratioSquare : Double = ((bitmapHeight * bitmapWidth) / MAX_SIZE.toDouble())
    if(ratioSquare <= 1) return bitmap else {

        var ratio = sqrt(ratioSquare)
        var requiredHeight : Int = round(bitmapHeight/ratio).toInt()
        var requiredWeight : Int = round(bitmapWidth/ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap,requiredWeight,requiredHeight,true)
    }

}