package com.decagon.facilitymanagementapp_group_two.model.data

data class Complain(
    var complainTitle: String,
    var complainBody: String,
    var complainDate: String
)

data class SingleComplaint(
    var userImage: Int,
    var complainName: String,
    var complaindetail: String,
    var compalindate: String
)
