package com.example.monitorcardiaco.registration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.monitorcardiaco.repository.IUserRepository

/**
 *
 * Provides the SleepDatabaseDao and context to the ViewModel.
 */
class RegistrationViewModelFactory(
    private val repository: IUserRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

