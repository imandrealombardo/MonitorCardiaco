package com.example.monitorcardiaco.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class CardioParams(

    @PrimaryKey
    @ColumnInfo(name="pathology")
    val pathology: String,

    @ColumnInfo(name="nyha_class")
    val nyhaClass: Int?,

    @ColumnInfo(name="intermacs_class")
    val intermacsClass: Int?,

    @ColumnInfo(name="blood_pressure")
    val bloodPressure: String?,

    @ColumnInfo(name="heart_rate")
    val heartRate: Int?
)