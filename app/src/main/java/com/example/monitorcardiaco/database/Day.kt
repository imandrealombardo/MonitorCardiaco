package com.example.monitorcardiaco.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import java.util.*

data class Day(

    @PrimaryKey
    @ColumnInfo(name="date")
    val date: Calendar = Calendar.getInstance(),

    @Embedded val lvad: Lvad?,

    @Embedded val bodyparams: BodyParams?,

    @Embedded val cardioparams: CardioParams?
    )