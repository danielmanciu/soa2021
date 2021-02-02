package com.soa.data.remote

import com.soa.data.model.User
import com.soa.data.model.dto.LoginUser
import com.soa.data.model.dto.Token
import com.soa.data.remote.AppConstants.Endpoints.LOGIN
import com.soa.data.remote.AppConstants.Endpoints.USER
import com.soa.data.remote.AppConstants.HttpHeaders.AUTHORIZATION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST(LOGIN)
    suspend fun login(@Body user: LoginUser): Response<Unit>

    @GET(USER)
    suspend fun getUser(@Header(AUTHORIZATION) token: String): Response<User>
}