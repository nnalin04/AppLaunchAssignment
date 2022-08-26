package com.example.applaunchassignment.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.applaunchassignment.data.Response
import com.example.applaunchassignment.data.api.WeatherAPI
import com.example.applaunchassignment.data.db.UserDB
import com.example.applaunchassignment.data.model.User
import com.example.applaunchassignment.data.model.WeatherAPIResponse
import com.example.applaunchassignment.utils.Constants
import com.example.applaunchassignment.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDB: UserDB,
    private val weatherAPI: WeatherAPI,
    @ApplicationContext private val context: Context
) {

    private val _userLiveData = MutableLiveData<Response<List<User>>>()
    val userLiveData: LiveData<Response<List<User>>>
        get() = _userLiveData

    private val _userByEmail = MutableLiveData<Response<List<User>>>()
    val userByEmail: LiveData<Response<List<User>>>
        get() = _userByEmail

    private val _weatherLiveData = MutableLiveData<Response<WeatherAPIResponse>>()
    val weatherLiveData: LiveData<Response<WeatherAPIResponse>>
        get() = _weatherLiveData

    suspend fun getUserData() {
        try {
            _userLiveData.postValue(Response.Loading())
            val result = userDB.getUserDAO().getUserEntity()
            _userLiveData.postValue(Response.Success(result))
        } catch (e: Exception) {
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

    suspend fun getWeatherData() {
        try {
            _weatherLiveData.postValue(Response.Loading())
            if (NetworkUtils.isInternetAvailable(context)) {
                val result = weatherAPI.getWeatherData(
                    Constants.Q_LAT,
                    Constants.Q_LON,
                    Constants.Q_UNITS,
                    Constants.Q_APP_ID
                )
                if (result.body() != null) {
                    _weatherLiveData.postValue(Response.Success(result.body()))
                }
            }
        } catch (e: Exception) {
            Log.e("MyTag", e.message.toString())
            _weatherLiveData.postValue(Response.Error(e.message.toString()))
        }
    }

}