package com.soa.page.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.observe
import com.soa.R
import com.soa.base.BaseFragment
import com.soa.utils.Outcome
import com.soa.utils.navigateTo
import com.soa.utils.showCancellableAlertDialog
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber

class LoginFragment : BaseFragment<LoginViewModel>(LoginViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        observeLoginResultLiveData()
    }

    private fun initLayout() {
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        var areFieldsCompleted = true
        if (loginUserNameEditText.text.isNullOrEmpty()) {
            loginUserNameEditText.error = getString(R.string.soa_name_validation_text)
            areFieldsCompleted = false
        }
        if (loginPasswordEditText.text.isNullOrEmpty()) {
            loginPasswordEditText.error = getString(R.string.soa_name_validation_text)
            areFieldsCompleted = false
        }
        if (areFieldsCompleted) {
            val userName = loginUserNameEditText.text.toString()
            val password = loginPasswordEditText.text.toString()
            viewModel.login(userName, password)
        }
    }

    private fun observeLoginResultLiveData() {
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            when (val result = it.consumeContent) {
                is Outcome.Progress -> {
                    Timber.i("login in progress")
                    loginOverlay.showLoading(R.string.soa_loggin_in)
                }
                is Outcome.Success -> {
                    Timber.i("login succeeded: ${result.data}")
                    loginOverlay.hideLoading()
                    navigateTo(LoginFragmentDirections.toVoteFragment())
                }
                is Outcome.Failure -> {
                    Timber.e("login failed: ${result.errorMessage}")
                    loginOverlay.hideLoading()
                    showCancellableAlertDialog("Could not log in. Please try again.")
                }
            }
        }
    }
}