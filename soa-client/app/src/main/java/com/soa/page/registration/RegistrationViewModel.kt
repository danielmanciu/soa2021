//package com.soa.page.registration
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.soa.data.repository.VoteRepository
//import com.soa.utils.Outcome
//import com.soa.utils.SingleEvent
//import com.soa.utils.handleApiResponse
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import java.io.File
//
//class RegistrationViewModel(private val repository: VoteRepository) : ViewModel() {
//
//    private val _registrationResultLiveData = MutableLiveData<SingleEvent<Outcome<String>>>()
//    val registrationResultLiveData: LiveData<SingleEvent<Outcome<String>>> =
//        _registrationResultLiveData
//
//    fun registerUser(name: String, imageFile: File) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _registrationResultLiveData.postValue(SingleEvent(Outcome.loading(true)))
//
//            handleApiResponse(
//                { repository.registerUser(name, imageFile) },
//                { result ->
//                    _registrationResultLiveData.postValue(SingleEvent(Outcome.success(result!!)))
//                },
//                { errorMessage ->
//                    _registrationResultLiveData.postValue(SingleEvent(Outcome.failure(errorMessage)))
//                }
//            )
//        }
//    }
//}