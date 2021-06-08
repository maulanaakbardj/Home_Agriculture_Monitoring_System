package com.cap0488.homeagriculturemonitoringsystem.ui.monitoring

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0488.homeagriculturemonitoringsystem.data.response.BtnResponse
import com.cap0488.homeagriculturemonitoringsystem.data.response.PostResponse
import com.cap0488.homeagriculturemonitoringsystem.data.response.Response
import com.reza.movieapi.helper.ApiConfig
import retrofit2.Call
import retrofit2.Callback

class MonitoringViewModel : ViewModel() {


    private val _plantData = MutableLiveData<Response>()
    val plantData: LiveData<Response> = _plantData

    private val _lamp = MutableLiveData<BtnResponse>()
    val lamp: LiveData<BtnResponse> = _lamp


    private val _isLoading = MutableLiveData<Boolean>()

    companion object{
        private const val username = "qwerty"

    }
    init {
        showMonitoring()
        showButtonCondition()
    }

    fun showButtonCondition(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getLampCondition()

        client.enqueue(object : Callback<BtnResponse> {
            override fun onResponse(
                    call: Call<BtnResponse>,
                    response: retrofit2.Response<BtnResponse>
            )
            {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _lamp.value = response.body()
                    Log.d("cekretro",response.body().toString())

                } else {

                    Log.e("cekretro", "onFailure: ${response.message()}")
                }


            }

            override fun onFailure(call: Call<BtnResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("cek","fail")

                Log.e("cek", "onFailure: ${t.message.toString()}")
            }

        })


    }

    fun showMonitoring() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMonitoringData()
        client.enqueue(object : Callback<Response> {
            override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
            )
            {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _plantData.value = response.body()
                   // Log.d("cekretro",response.body().toString())

                } else {

                    //Log.e("cekretro", "onFailure: ${response.message()}")
                }


            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _isLoading.value = false
                Log.d("cek","fail")

                Log.e("cek", "onFailure: ${t.message.toString()}")
            }

        })

    }

    fun postCondition(btn:String,state:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().changeLampCondition("qwerty", btn,state)
        client.enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: retrofit2.Response<PostResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("cekpost","post dulu")


                } else {
                    Log.e("cekpost", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("cekpost", "onFailure: ${t.message.toString()}")
            }
        })
    }
}