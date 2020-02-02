package com.example.monitorcardiaco

import android.app.Application
import com.example.monitorcardiaco.repository.IUserRepository

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 * TODO: Setup Timber
 */
class MonitorCardiacoApplication: Application() {

    val userRepository: IUserRepository
        get() = ServiceLocator.provideUserRepository(this)

    override fun onCreate() {
        super.onCreate()
       //TODO if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

}