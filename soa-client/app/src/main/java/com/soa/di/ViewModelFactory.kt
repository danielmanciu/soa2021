package com.soa.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soa.data.repository.VoteRepository
import com.soa.page.vote.VoteViewModel
import com.soa.page.login.LoginViewModel
import com.soa.page.splash.SplashViewModel

class ViewModelFactory(
    private val preferences: SharedPreferences,
    private val repository: VoteRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                SplashViewModel(preferences, repository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(preferences, repository) as T
            modelClass.isAssignableFrom(VoteViewModel::class.java) ->
                VoteViewModel(preferences, repository) as T
//            modelClass.isAssignableFrom(RegistrationViewModel::class.java) ->
//                RegistrationViewModel(repository) as T
            else -> throw ClassNotFoundException("Could not create ViewModel of class: $modelClass")
        }
    }
}