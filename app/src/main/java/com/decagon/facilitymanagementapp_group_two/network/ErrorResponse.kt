package com.decagon.facilitymanagementapp_group_two.network

/**
 * Data class for modeling errors
 */
data class ErrorResponse(
        val errorDescription: String,
        val causes: Map<String, String> = emptyMap()
)