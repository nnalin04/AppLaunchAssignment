package com.example.applaunchassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.applaunchassignment.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
}