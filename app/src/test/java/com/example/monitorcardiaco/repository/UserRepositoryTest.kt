package com.example.monitorcardiaco.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monitorcardiaco.database.FakeUserDatabaseDao
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.getOrAwaitValue
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val _user1 = MutableLiveData<User>()

    val user1: LiveData<User>
    get() = _user1

    private lateinit var dataSource: FakeUserDatabaseDao

    // Class under test
    private lateinit var userRepository: UserRepository

    @Before
    fun createRepository() {
        _user1.value = User(0,"Name", "Surname","LVAD",
            "Maschio", "18/11/1980", "Italy","Italy", null, null, null)

        dataSource = FakeUserDatabaseDao(_user1)
        // TODO Dispatchers.Unconfined should be replaced with Dispatchers.Main
        //  this requires understanding more about coroutines + testing
        //  so we will keep this as Unconfined for now.
        userRepository = UserRepository(dataSource, Dispatchers.Unconfined)

        runBlocking { userRepository.insertUser(_user1.value!!) }
    }

    @Test
    fun getUser_requestUserFromDataSource() {

        // When user is requested from the userRepository
        val user = userRepository.user

        // Then assert that they are the same
        assertEquals(user.getOrAwaitValue(), _user1.value)
    }

}