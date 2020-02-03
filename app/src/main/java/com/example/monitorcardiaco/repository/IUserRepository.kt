package com.example.monitorcardiaco.repository

import androidx.lifecycle.LiveData
import com.example.monitorcardiaco.database.User

interface IUserRepository {
    val user: LiveData<User>
//    val userWithDays: LiveData<List<UserWithDays>>

    suspend fun insertUser(user: User)
   // suspend fun getUser(): LiveData<User>
   // suspend fun getUser(id: String): LiveData<User>

    suspend fun clearDatabase()
}