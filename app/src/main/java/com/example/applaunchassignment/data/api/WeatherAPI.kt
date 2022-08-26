package com.example.applaunchassignment.data.api

import com.example.applaunchassignment.data.model.WeatherAPIResponse
import com.example.applaunchassignment.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("onecall")
    suspend fun getWeatherData(
        @Query(Constants.LAT) lat : String,
        @Query(Constants.LON) lon : String,
        @Query(Constants.UNITS) units : String,
        @Query(Constants.APP_ID) appId : String
    ) : Response<WeatherAPIResponse>
}