package com.decagon.facilitymanagementapp_group_two.model.data

import com.decagon.facilitymanagementapp_group_two.model.data.entities.Feeds

data class DataX(
    val currentPage: Int,
    val items: List<Feeds>,
    val itemsPerPage: Int,
    val totalNumberOfItems: Int,
    val totalNumberOfPages: Int
)