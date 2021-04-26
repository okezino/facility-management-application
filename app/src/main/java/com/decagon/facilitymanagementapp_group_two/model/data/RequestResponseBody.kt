package com.decagon.facilitymanagementapp_group_two.model.data

import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

data class RequestResponseBody(
    val `data`: Request,
    val message: String,
    val success: Boolean
)
