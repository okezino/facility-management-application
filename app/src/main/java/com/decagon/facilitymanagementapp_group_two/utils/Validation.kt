package com.decagon.facilitymanagementapp_group_two.utils

import com.decagon.facilitymanagementapp_group_two.model.data.UpdateProfileBody

fun subjectValidation(s: String): Boolean {
    var value = true
    if (s.isEmpty()) value = false
    if (s.trim().length > 120) value = false

    return value
}

fun descriptionValidation(s: String): Boolean {
    var value = true
    if (s.isEmpty()) value = false
    if (s.trim().length > 300) value = false

    return value
}

fun feedSelectionValidation(s: String): Boolean {
    return s != "select a category..."
}

fun squadInputValidation(s: String): Boolean {
    var value = true
    var digits = s.takeLast(3)
    if (s.length < 5 || s[0] != 'S' || s[1] != 'Q' || s.isEmpty()) value = false
    for (i in digits) if (!i.isDigit()) value = false
    return value
}

fun stackValidation(s: String): Boolean {
    val validStack = listOf(".NET", "PYTHON", "NODE", "IOS", "JAVA", "QUALITY ASSURANCE", "ANDROID", "QA")
    return validStack.contains(s)
}

fun phoneNumberValidator(phone: String): Boolean {
    var value = false
     if ((
        phone.take(6) == "+23470" ||
            phone.take(6) == "+23480" ||
            phone.take(6) == "+23490" ||
            phone.take(6) == "+23481"
        ) &&
        phone.length == 14
    ) value = true
    return value
}

fun UpdateProfileBody.inputValidation(): String {
    var message = "Success"

    if (!phoneNumberValidator(mobile)) message = "Invalid Phone Format"

    if (!stackValidation(stack)) message = "Invalid Stack Format"

    if (!squadInputValidation(squad)) message = "Invalid Squad Format"

    return message
}
