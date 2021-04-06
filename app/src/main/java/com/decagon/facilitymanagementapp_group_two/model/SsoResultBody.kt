package com.decagon.facilitymanagementapp_group_two.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SsoResultBody(val firstName : String, val lastName : String, val email : String) : Parcelable{
    override fun toString(): String {
        return "Name: $firstName  Email: $email"
    }
}