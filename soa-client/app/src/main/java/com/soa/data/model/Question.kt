package com.soa.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(@SerializedName("questionId") val id: Long, val text: String, val answers: List<Answer>) : Parcelable