package com.soa.data.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vote(val userId: Long, val questionId: Long, val answerId: Long) : Parcelable