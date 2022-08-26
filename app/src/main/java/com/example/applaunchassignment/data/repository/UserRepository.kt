package com.example.applaunchassignment.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.applaunchassignment.data.Response
import com.example.applaunchassignment.data.db.UserDB
import com.example.applaunchassignment.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDB: UserDB
) {

    private val _userLiveData = MutableLiveData<Response<List<User>>>()
    val userLiveData: LiveData<Response<List<User>>>
        get() = _userLiveData

    private val _userByEmail = MutableLiveData<Response<List<User>>>()
    val userByEmail: LiveData<Response<List<User>>>
        get() = _userByEmail

    suspend fun getUserData() {
        try {
            _userLiveData.postValue(Response.Loading())
            val result = userDB.getUserDAO().getUserEntity()
            _userLiveData.postValue(Response.Success(result))
        } catch (e : Exception) {
            Log.d("MyTag", e.printStackTrace().toString())
            _userLiveData.postValue(Response.Error("Some Error Occurred"))
        }
    }

    suspend fun deleteUser(user: User) {
        try {
            userDB.getUserDAO().delete(user)
            getUserData()
        } catch (e: Exception) {
            Log.d("MyTag", e.printStackTrace().toString())
            _userLiveData.postValue(Response.Error("Some Error Occurred"))
        }
    }

    suspend fun insertUser(user: User) {
        try {
            userDB.getUserDAO().insertUserEntity(user)
            getUserData()
        } catch (e: Exception) {
            Log.d("MyTag", e.printStackTrace().toString())
            _userLiveData.postValue(Response.Error("Some Error Occurred"))
        }
    }

     suspend fun getUserByEmail(email: String) {
        _userByEmail.postValue(Response.Success(userDB.getUserDAO().getUserByEmail(email)))
    }

}