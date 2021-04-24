package com.krayapp.materialpj.viewmodel

import com.krayapp.materialpj.retrofit.ResponseData

sealed class PictureData {
    data class Success(val serverResponseData: ResponseData) : PictureData()
    data class Error(val error: Throwable) : PictureData()
    data class Loading(val progress: Int?) : PictureData()
}