package com.example.applaunchassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applaunchassignment.data.model.User
import com.example.applaunchassignment.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val userList = repository.userLiveData

    val userByEmail = repository.userByEmail

    val weatherReport = repository.weatherLiveData

    init {
        loadUserData()
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getUserByEmail(email: String){
        viewModelScope.launch {
            repository.getUserByEmail(email)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            repository.getUserData()
        }
    }

    fun loadWeatherReport() {
        viewModelScope.launch {
            repository.getWeatherData()
        }
    }
}