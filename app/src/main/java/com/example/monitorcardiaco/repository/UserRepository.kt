package com.example.monitorcardiaco.repository

import androidx.lifecycle.LiveData
import com.example.monitorcardiaco.database.User
import com.example.monitorcardiaco.database.UserDatabaseDao
import kotlinx.coroutines.*

class UserRepository(private val dataSource: UserDatabaseDao,
                     private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    IUserRepository {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    override val user: LiveData<User>
        get() = dataSource.getUser()


//    companion object {
//
//        @Volatile
//        private var INSTANCE: UserRepository? = null
//
//        fun getRepository(app: Application): UserRepository {
//            return INSTANCE ?: synchronized(this) {
//                val database = UserDatabase.getInstance(app)
//                UserRepository(database.userDatabaseDao).also {
//                    INSTANCE = it
//                }
//            }
//        }
//    }

    override suspend fun insertUser(user: User) {
        dataSource.insert(user)
    }
//
//    override suspend fun getUser(): LiveData<User> = withContext(ioDispatcher) {
//        return@withContext dataSource.getUser() //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override suspend fun getUser(id: String): LiveData<User> = withContext(ioDispatcher) {
//        return@withContext dataSource.getUser(email)
//    }

    override suspend fun clearDatabase() {
        dataSource.clear()
    }

}