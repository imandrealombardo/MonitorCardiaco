package com.example.monitorcardiaco.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monitorcardiaco.database.UserDatabaseDao
import com.example.monitorcardiaco.repository.IUserRepository

class OverviewViewModelFactory(
    private val dataSource: UserDatabaseDao,
    private val repository: IUserRepository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(dataSource, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}