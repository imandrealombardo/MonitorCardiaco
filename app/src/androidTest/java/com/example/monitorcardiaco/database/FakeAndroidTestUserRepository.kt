package com.example.monitorcardiaco.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monitorcardiaco.repository.IUserRepository
import kotlinx.coroutines.runBlocking

class FakeAndroidTestUserRepository : IUserRepository {

    var _user = MutableLiveData<User>()

    val user1: LiveData<User>
        get() = _user

    fun addUser(user: User) {
        _user.value = user
    }

    override val user: LiveData<User>
        get() = _user

    override suspend fun insertUser(user: User) {
        runBlocking { _user.postValue(user) }
    }

    override suspend fun clearDatabase() {
       runBlocking { _user.value = User(0,"", "", "", "", "", "", "", null, null, null) }
    }

}