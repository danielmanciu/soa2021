package com.soa.data.remote

object AppConstants {

    const val LOGIN_API_BASE_URL = "http://192.168.0.102:8130"
    const val VOTE_API_BASE_URL = "http://192.168.0.102:8131"

    const val ACCEPT_HEADER = "Accept"
    const val APPLICATION_JSON = "application/json"
    const val GENERIC_ERROR_MESSAGE = "Something went wrong."
    const val SERVER_ERROR_MESSAGE = "Could not connect to server."
    const val EMPTY_STRING = ""

    object Endpoints {
        const val LOGIN = "/login"
        const val USER = "/user"
        const val QUESTION = "/first"
        const val VOTE = "/vote"
    }

    object HttpHeaders {
        const val AUTHORIZATION = "Authorization"
    }

    object SharedPreferences {
        const val KEY_TOKEN = "token"
    }

    object Retrofit {
        const val RETROFIT_LOGIN = "login"
        const val RETROFIT_VOTE = "vote"
    }
}