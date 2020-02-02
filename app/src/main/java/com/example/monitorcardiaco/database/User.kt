package com.example.monitorcardiaco.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (

            @PrimaryKey(autoGenerate = true)
            var id: Int,

//            @ColumnInfo(name="email")
//            var email: String,

//            @ColumnInfo(name="password")
//            var password: String,

            @ColumnInfo(name="name")
            var name: String,

            @ColumnInfo(name="surname")
            var surname: String,

            @ColumnInfo(name="type")
            var type: String,

            @ColumnInfo(name="gender")
            var gender: String,

            @ColumnInfo(name="birthdate")
            var birthdate: String,

            @ColumnInfo(name="birthplace")
            var birthplace: String,

            @ColumnInfo(name="residence")
            var residence: String,

            @Embedded val lvad: Lvad?,

            @Embedded val bodyparams: BodyParams?,

            @Embedded val cardioparams: CardioParams?

            )
{
    val isEmpty
        get() = name.isEmpty() || surname.isEmpty() ||
                type.isEmpty() || gender.isEmpty() || birthdate.isEmpty() || birthplace.isEmpty() ||
                residence.isEmpty()
}