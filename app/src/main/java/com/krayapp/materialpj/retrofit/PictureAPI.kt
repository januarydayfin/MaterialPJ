package com.krayapp.materialpj.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureAPI {
    @GET("planetary/apod")
    fun getPicture(
        @Query("api_key")apiKey: String,
        @Query("date")date:String?
    ): Call<ResponseData>
}