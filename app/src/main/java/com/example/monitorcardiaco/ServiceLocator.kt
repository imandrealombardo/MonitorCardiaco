package com.example.monitorcardiaco

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.monitorcardiaco.database.UserDatabase
import com.example.monitorcardiaco.database.UserDatabaseDao
import com.example.monitorcardiaco.repository.IUserRepository
import com.example.monitorcardiaco.repository.UserRepository
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private var database: UserDatabase? = null
    @Volatile
    var userRepository: IUserRepository? = null
    @VisibleForTesting set

    private val lock = Any()

    fun provideUserRepository(context: Context): IUserRepository {
        synchronized(this) {
            return userRepository ?: createUserRepository(context)
        }
    }

    private fun createUserRepository(context: Context): IUserRepository {
        val newRepo = UserRepository(createUserDataSource(context))
        userRepository = newRepo
        return newRepo
    }

    private fun createUserDataSource(context: Context) : UserDatabaseDao {
        val database = database ?: createDataBase(context)
        return database.userDatabaseDao
    }

    private fun createDataBase(context: Context): UserDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java, "user_database"
        )
            .fallbackToDestructiveMigration()
            .build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
//            runBlocking {
//                TasksRemoteDataSource.deleteAllTasks()
//            }
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                //close()
            }
            database = null
            userRepository = null
        }
    }
}