package com.example.applaunchassignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val firstName: String,
    val lastName: String,
    @PrimaryKey
    val email: String
)
