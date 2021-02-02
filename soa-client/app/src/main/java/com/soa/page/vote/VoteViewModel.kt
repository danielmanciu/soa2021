package com.soa.page.vote

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soa.data.model.Answer
import com.soa.data.model.Question
import com.soa.data.model.dto.Vote
import com.soa.data.remote.AppConstants.EMPTY_STRING
import com.soa.data.repository.VoteRepository
import com.soa.utils.Outcome
import com.soa.utils.SingleEvent
import com.soa.utils.handleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VoteViewModel(
    private val preferences: SharedPreferences,
    private val repository: VoteRepository
) : ViewModel() {

    val userName = repository.currentUser?.userName ?: EMPTY_STRING
    val userId = repository.currentUser?.id
    var questionId: Long? = null

    private val _questionResultLiveData = MutableLiveData<SingleEvent<Outcome<Question>>>()
    val questionResultLiveData: LiveData<SingleEvent<Outcome<Question>>> = _questionResultLiveData

    private val _voteResultLiveData = MutableLiveData<SingleEvent<Outcome<List<Answer>>>>()
    val voteResultLiveData: LiveData<SingleEvent<Outcome<List<Answer>>>> = _voteResultLiveData

    fun loadQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            _questionResultLiveData.postValue(SingleEvent(Outcome.loading(true)))
            delay(1000)
            handleApiResponse(
                { repository.getQuestion() },
                { question ->
                    questionId = question?.id
                    _questionResultLiveData.postValue(SingleEvent(Outcome.success(question!!)))
                },
                {
                    _questionResultLiveData.postValue(SingleEvent(Outcome.failure("Something went wrong.")))
                }
            )
        }
    }

    fun vote(answerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _voteResultLiveData.postValue(SingleEvent(Outcome.loading(true)))
            delay(1000)
            handleApiResponse(
                { repository.vote(Vote(userId!!, questionId!!, answerId)) },
                { answerList ->
                    _voteResultLiveData.postValue(SingleEvent(Outcome.success(answerList!!)))
                },
                {
                    _voteResultLiveData.postValue(SingleEvent(Outcome.failure("Something went wrong.")))
                }
            )
        }
    }

    fun logout() {
        repository.token = null
        repository.currentUser = null
        preferences.edit().clear().apply()
    }
}