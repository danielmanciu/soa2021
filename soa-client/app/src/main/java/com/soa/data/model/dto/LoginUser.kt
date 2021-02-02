package com.soa.data.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginUser(val userName: String, val password: String) : Parcelable