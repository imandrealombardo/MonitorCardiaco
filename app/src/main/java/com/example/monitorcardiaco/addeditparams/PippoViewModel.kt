package com.example.monitorcardiaco.addeditparams

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.database.UserDatabaseDao
import com.example.monitorcardiaco.repository.IUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PippoViewModel(private val userRepository: IUserRepository
) : ViewModel() {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private val _user = userRepository.user

    val user: LiveData<User>
        get() = _user

    private fun updateUser() = uiScope.launch {
        userRepository.insertUser(user.value!!)
    }
}