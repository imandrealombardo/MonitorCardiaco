package com.example.monitorcardiaco.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lvad(

    @PrimaryKey
    @ColumnInfo(name="device")
    val device: String,

    @ColumnInfo(name="rpm")
    val rpm: Int?,

    @ColumnInfo(name="watt")
    val watt: Int?,

    @ColumnInfo(name="flux")
    val flux: Int?,

    @ColumnInfo(name="pi")
    val pi: Double?,

    @ColumnInfo(name="max")
    val max: Int?,

    @ColumnInfo(name="min")
    val min: Int?)