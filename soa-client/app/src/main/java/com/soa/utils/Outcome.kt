package com.soa.utils

sealed class Outcome<T> {

    data class Progress<T>(var isLoading: Boolean) : Outcome<T>()
    data class Success<T>(var data: T) : Outcome<T>()
    data class Failure<T>(val errorMessage: String) : Outcome<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): Outcome<T> = Progress(isLoading)
        fun <T> success(data: T): Outcome<T> = Success(data)
        fun <T> failure(message: String): Outcome<T> = Failure(message)
    }
}