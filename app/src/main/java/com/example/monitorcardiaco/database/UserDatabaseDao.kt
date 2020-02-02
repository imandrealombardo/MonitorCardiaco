package com.example.monitorcardiaco.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.monitorcardiaco.database.User

@Dao
interface UserDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * from user_table WHERE id = :key")
    fun getUser(key: Int): LiveData<User>

    @Query("SELECT * from user_table")
    fun getUser(): LiveData<User>

    @Query("DELETE FROM user_table")
    suspend fun clear()

    @Query("SELECT * from user_table")
    fun getAllUsers(): LiveData<List<User>>

    // Per heart_rate e step_counter inserirli come LiveData, in modo da aggiornarli automaticamente
    // https://classroom.udacity.com/courses/ud9012/lessons/fcd3f9aa-3632-4713-a299-ea39939d6fd7/concepts/b74dc5e4-19ca-4acc-99f2-4c9245b3f05a
    // Controllare in fragment_sleep_tracker come gestiva l'aggiornamento automatico della lista
}
