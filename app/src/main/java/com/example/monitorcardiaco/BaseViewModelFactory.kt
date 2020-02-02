package com.example.monitorcardiaco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * BaseViewModelFactory that receives the creation logic from the outside, through a lambda,
 * and just calls it when necessary
 */
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}