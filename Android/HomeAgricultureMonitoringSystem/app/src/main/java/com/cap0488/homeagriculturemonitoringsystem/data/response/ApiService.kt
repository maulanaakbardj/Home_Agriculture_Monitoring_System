package com.reza.movieapi.helper

import com.cap0488.homeagriculturemonitoringsystem.data.response.Response
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("data/user=qwerty")
    fun getMonitoringData(): Call<Response>




}