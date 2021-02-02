package com.soa.page.splash

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soa.data.remote.AppConstants.EMPTY_STRING
import com.soa.data.remote.AppConstants.SharedPreferences.KEY_TOKEN
import com.soa.data.repository.VoteRepository
import com.soa.utils.Outcome
import com.soa.utils.SingleEvent
import com.soa.utils.handleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel(
    private val preferences: SharedPreferences,
    private val repository: VoteRepository
) : ViewModel() {

    private val _splashResultLiveData = MutableLiveData<SingleEvent<Outcome<Unit>>>()
    val splashResultLiveData: LiveData<SingleEvent<Outcome<Unit>>> = _splashResultLiveData

    fun checkToken() {
        viewModelScope.launch(Dispatchers.IO) {
            _splashResultLiveData.postValue(SingleEvent(Outcome.loading(true)))
            delay(1000)
            val tokenPrefs = loadToken().also {
                Timber.i("XXX $it")
            }
            if (tokenPrefs.isNullOrBlank()) {
                repository.token = null
                repository.currentUser = null
                _splashResultLiveData.postValue(SingleEvent(Outcome.failure("Not signed in.")))
            } else {
                repository.token = tokenPrefs
                handleApiResponse(
                    { repository.getUser() },
                    { user ->
                        Timber.i("XXX user: $user")
                        repository.currentUser = user
                        _splashResultLiveData.postValue(SingleEvent(Outcome.success(Unit)))
                    },
                    {
                        _splashResultLiveData.postValue(SingleEvent(Outcome.failure("Something went wrong.")))
                    }
                )
            }
        }
    }

    private fun loadToken(): String? = preferences.getString(KEY_TOKEN, EMPTY_STRING)
}