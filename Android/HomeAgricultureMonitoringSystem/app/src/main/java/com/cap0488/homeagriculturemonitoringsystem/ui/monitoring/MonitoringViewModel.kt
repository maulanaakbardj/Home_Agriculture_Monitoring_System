package com.cap0488.homeagriculturemonitoringsystem.ui.monitoring

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0488.homeagriculturemonitoringsystem.data.response.Response
import com.reza.movieapi.helper.ApiConfig
import retrofit2.Call
import retrofit2.Callback

class MonitoringViewModel : ViewModel() {


    private val _plantData = MutableLiveData<Response>()
    val plantData: LiveData<Response> = _plantData

    private val _isLoading = MutableLiveData<Boolean>()

    companion object{
        private const val username = "qwerty"

    }
    init {
        showMonitoring()
    }

    fun showMonitoring() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMonitoringData()

        Log.d("cekclient",client.toString())
        client.enqueue(object : Callback<Response> {
            override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
            )
            {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _plantData.value = response.body()
                    Log.d("cekretro",response.body().toString())

                } else {

                    Log.e("cekretro", "onFailure: ${response.message()}")
                }


            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _isLoading.value = false
                Log.d("cek","fail")

                Log.e("cek", "onFailure: ${t.message.toString()}")
            }

        })

    }
}