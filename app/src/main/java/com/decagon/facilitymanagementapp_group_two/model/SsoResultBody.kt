package com.decagon.facilitymanagementapp_group_two.model

data class SsoResultBody(val firstName : String, val lastName : String, val email : String){
    override fun toString(): String {
        return "Name: $firstName  Email: $email"
    }
}