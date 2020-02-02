package com.example.monitorcardiaco.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeUserDatabaseDao(private var fakeUser: MutableLiveData<User>) : UserDatabaseDao {
    override suspend fun insert(user: User) {
        fakeUser.value = user
    }

    override fun getUser(key: Int): LiveData<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): LiveData<User> {
        return fakeUser
    }

    override suspend fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllUsers(): LiveData<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}