package com.reza.movieapi.helper

import com.cap0488.homeagriculturemonitoringsystem.data.response.BtnResponse
import com.cap0488.homeagriculturemonitoringsystem.data.response.PostResponse
import com.cap0488.homeagriculturemonitoringsystem.data.response.Response
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("data/user=qwerty")
    fun getMonitoringData(): Call<Response>

    @GET("sensor/get-button/user=qwerty")
    fun getLampCondition():Call<BtnResponse>

    @FormUrlEncoded
    @POST("sensor/button")
    fun changeLampCondition(
            @Field("username") username: String,
            @Field("button") button: String,
            @Field("state") review: String)
    :Call<PostResponse>

}