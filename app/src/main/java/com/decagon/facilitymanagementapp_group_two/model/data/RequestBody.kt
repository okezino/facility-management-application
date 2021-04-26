package com.decagon.facilitymanagementapp_group_two.model.data

data class RequestBody(
    val title : String,
    val question : String,
    val userId : String? = null,
    val type : String
)