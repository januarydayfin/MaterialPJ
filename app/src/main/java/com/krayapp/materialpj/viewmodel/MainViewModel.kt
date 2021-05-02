package com.krayapp.materialpj.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krayapp.materialpj.retrofit.RetrofitImpl
import com.krayapp.materialpj.BuildConfig
import com.krayapp.materialpj.retrofit.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error

class MainViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {
    fun getLiveData(date:String?, whattaDay:String?): LiveData<PictureData> {
        sendServerRequest(date, whattaDay)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(date:String?, whattaDay:String?) {
        liveDataForViewToObserve.value = PictureData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureData.Error(Throwable("You Need API key"))
        } else {
            retrofitImpl.getRetrofitImpl()
                .getPicture(apiKey, date)
                .enqueue(object : Callback<ResponseData> {
                    override fun onResponse(
                        call: Call<ResponseData>,
                        response: Response<ResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.value = PictureData.Success(response.body()!!,whattaDay)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserve.value =
                                    PictureData.Error(Throwable("Неизвестная ошибка"))
                            } else {
                                liveDataForViewToObserve.value =
                                    PictureData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        liveDataForViewToObserve.value = PictureData.Error(t)
                    }
                })
        }
    }
}