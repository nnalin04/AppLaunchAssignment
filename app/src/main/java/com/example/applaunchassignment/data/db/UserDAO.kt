package com.example.applaunchassignment.data.db

import androidx.room.*
import com.example.applaunchassignment.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEntity(user: User)

    @Query("SELECT * FROM User")
    suspend fun getUserEntity(): List<User>

    @Query("SELECT * FROM User WHERE email LIKE :email")
    suspend fun getUserByEmail(email: String) : List<User>

    @Delete
    suspend fun delete(user: User)
}