package com.cap0488.homeagriculturemonitoringsystem.data.response

data class Response(
        val temperature: String,
        val humidity: String,
        val soilMoisture: String,
        val username: String
)

data class BtnResponse(
        val lamp: String,
        val pump: String
)

data class PostResponse(
        val username: String,
        val button: String,
        val state:String
)