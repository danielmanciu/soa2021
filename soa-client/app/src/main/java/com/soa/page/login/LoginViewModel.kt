package com.soa.page.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soa.data.remote.AppConstants.HttpHeaders.AUTHORIZATION
import com.soa.data.remote.AppConstants.SharedPreferences.KEY_TOKEN
import com.soa.data.repository.VoteRepository
import com.soa.utils.Outcome
import com.soa.utils.SingleEvent
import com.soa.utils.handleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val preferences: SharedPreferences,
    private val repository: VoteRepository
) : ViewModel() {

    private val _loginResultLiveData = MutableLiveData<SingleEvent<Outcome<Unit>>>()
    val loginResultLiveData: LiveData<SingleEvent<Outcome<Unit>>> = _loginResultLiveData

    fun login(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _loginResultLiveData.postValue(SingleEvent(Outcome.loading(true)))

            val response = repository.login(userName, password)

            if (response.isSuccessful) {
                val token = response.headers()[AUTHORIZATION]!!.split(' ').last()
                    saveToken(token)
                    getCurrentUser()
            }
            else {
                _loginResultLiveData.postValue(SingleEvent(Outcome.failure("Couldn't log in.")))
                repository.token = null
                repository.currentUser = null
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            handleApiResponse(
                { repository.getUser() },
                { user ->
                    Timber.i("XXX user: $user")
                    repository.currentUser = user
                    _loginResultLiveData.postValue(SingleEvent(Outcome.success(Unit)))
                },
                { errorMessage ->
                    _loginResultLiveData.postValue(SingleEvent(Outcome.failure(errorMessage)))
                    repository.token = null
                    repository.currentUser = null
                }
            )
        }
    }

    private fun saveToken(token: String) {
        repository.token = token
        preferences.edit().run {
            putString(KEY_TOKEN, token)
            apply()
        }
    }
}