package com.decagon.facilitymanagementapp_group_two.utils

import android.widget.TextView

fun subjectValidation(s : String) : Boolean{
    var value = true
    if(s.isEmpty()) value = false
    if(s.trim().length > 120) value = false

    return value
}

fun descriptionValidation(s : String) : Boolean{
    var value = true
    if(s.isEmpty()) value = false
    if(s.trim().length > 300) value = false

    return value

}

fun feedSelectionValidation(s : String) : Boolean{
    return s != "Select a Category..."
}