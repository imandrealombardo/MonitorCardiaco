package com.example.monitorcardiaco.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monitorcardiaco.Event
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.database.UserDatabase
import com.example.monitorcardiaco.database.UserDatabaseDao
import com.example.monitorcardiaco.repository.IUserRepository
import com.example.monitorcardiaco.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking

class OverviewViewModel(val database: UserDatabaseDao,
                        private val userRepository: IUserRepository
) : ViewModel() {

    private val _user = userRepository.user

    var hasLvad = MutableLiveData<Boolean>()

    var isRotated = MutableLiveData<Boolean>()

    val user: LiveData<User>
        get() = _user


    init {
    }

    fun onAddParams() {

    }

//    val usersString = Transformations.map(users1) { users ->
//        formatUsers(users, application.resources)
//    }

}