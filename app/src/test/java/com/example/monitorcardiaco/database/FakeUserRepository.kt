package com.example.monitorcardiaco.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monitorcardiaco.repository.IUserRepository
import kotlinx.coroutines.runBlocking

class FakeUserRepository (private val dataSource: FakeUserDatabaseDao) : IUserRepository {

    override val user: LiveData<User>
        get() = dataSource.getUser()

    override suspend fun insertUser(newUser: User) {
        dataSource.insert(newUser)
    }

    override suspend fun clearDatabase() {
       dataSource.clear()
    }

}