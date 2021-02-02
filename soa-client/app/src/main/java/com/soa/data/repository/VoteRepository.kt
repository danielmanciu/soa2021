package com.soa.data.repository

import android.util.Base64
import com.soa.data.model.Answer
import com.soa.data.model.Question
import com.soa.data.model.dto.LoginUser
import com.soa.data.model.User
import com.soa.data.model.dto.Vote
import com.soa.data.remote.AppConstants.EMPTY_STRING
import com.soa.data.remote.LoginApi
import com.soa.data.remote.VoteApi
import retrofit2.Response

class VoteRepository(private val loginApi: LoginApi, private val voteApi: VoteApi) {

    var currentUser: User? = null
    var token: String? = null

    suspend fun login(username: String, password: String): Response<Unit> {
        val userNameEncoded = Base64.encodeToString(username.toByteArray(), Base64.NO_WRAP)
        val passwordEncoded = Base64.encodeToString(password.toByteArray(), Base64.NO_WRAP)
        return loginApi.login(LoginUser(userNameEncoded, passwordEncoded))
    }

    suspend fun getUser(): Response<User> = loginApi.getUser(token ?: EMPTY_STRING)

    suspend fun getQuestion(): Response<Question> = voteApi.getQuestion(token ?: EMPTY_STRING)

    suspend fun vote(vote: Vote): Response<List<Answer>> = voteApi.vote(token ?: EMPTY_STRING, vote)
}
