package com.krayapp.materialpj.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureInfo(
    val date: String?,
    val explanation: String?,
    val mediaType: String?,
    val title: String?,
    val url: String?,
    val hdurl: String?,
    val whattaday:String?
):Parcelable