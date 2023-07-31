package com.example.pendaftaranpondokpesantrenapp.model

import android.os.Parcelable
import android.view.ViewParent
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "registration_table")
data class Registration (
    @PrimaryKey(autoGenerate = true)
    val id: Int= 0,
    val name: String,
    val address: String,
    val phone: String,
    val parent: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable