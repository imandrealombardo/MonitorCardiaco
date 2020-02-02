package com.example.monitorcardiaco.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class BodyParams(

    @PrimaryKey
    @ColumnInfo(name="height")
    val height: Int,

    @ColumnInfo(name="weight")
    val weight: Int?,

    @ColumnInfo(name="steps")
    val steps: Int?,

    @ColumnInfo(name="speed")
    val speed: Int?,

    @ColumnInfo(name="calories")
    val calories: Int?
)