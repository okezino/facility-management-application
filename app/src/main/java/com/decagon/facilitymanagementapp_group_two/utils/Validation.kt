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



fun squadInputValidation(s : String) : Boolean {
    var value = true
    if(s.length < 3  || s.isEmpty()) value = false
    return value
}

fun stackValidation(s: String) : Boolean {
    val validStack = listOf(".NET","PYTHON","NODE","IOS","JAVA","QUALITY ASSURANCE","ANDROID")
    return validStack.contains(s)
}

fun phoneNumberValidator(phone: String) : Boolean {
      var value = false

     if((phone.take(3) == "070" ||
                phone.take(3) == "080" ||
                phone.take(3) == "090" ||
                phone.take(3) == "081" )&&
        phone.length == 11) value =  true

    else if  ((phone.take(5) == "23470" ||
            phone.take(5) == "23480"||
            phone.take(5) == "23490"||
            phone.take(5) == "23481" )
            && phone.length == 13) value = true
    return value

}