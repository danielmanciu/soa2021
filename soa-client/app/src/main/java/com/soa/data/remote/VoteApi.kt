package com.soa.data.remote

import com.soa.data.model.Answer
import com.soa.data.model.Question
import com.soa.data.model.dto.Vote
import com.soa.data.remote.AppConstants.Endpoints.QUESTION
import com.soa.data.remote.AppConstants.Endpoints.VOTE
import com.soa.data.remote.AppConstants.HttpHeaders.AUTHORIZATION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface VoteApi {

    @GET(QUESTION)
    suspend fun getQuestion(@Header(AUTHORIZATION) token: String): Response<Question>

    @POST(VOTE)
    suspend fun vote(@Header(AUTHORIZATION) token: String, @Body vote: Vote): Response<List<Answer>>
}