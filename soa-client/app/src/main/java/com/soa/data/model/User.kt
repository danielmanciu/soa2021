package com.soa.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(@SerializedName("userId") val id: Long, val userName: String, val password: String) : Parcelable