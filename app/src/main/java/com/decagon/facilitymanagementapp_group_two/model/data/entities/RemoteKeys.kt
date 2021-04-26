package com.decagon.facilitymanagementapp_group_two.model.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val id: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)


@Entity(tableName = "complain_remote_keys")
data class ComplainRemoteKeys(
    @PrimaryKey
    val id: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity(tableName = "apartment_remote_keys")
data class ApartmentRemoteKeys(
    @PrimaryKey
    val id: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity(tableName = "appliance_remote_keys")
data class ApplianceRemoteKeys(
    @PrimaryKey
    val id: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)

@Entity(tableName = "others_remote_keys")
data class OthersRemoteKeys(
    @PrimaryKey
    val id: Long?,
    val prevKey: Int?,
    val nextKey: Int?
)