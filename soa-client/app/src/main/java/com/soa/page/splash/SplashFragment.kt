package com.soa.page.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.soa.R
import com.soa.base.BaseFragment
import com.soa.utils.Outcome
import com.soa.utils.navigateTo

class SplashFragment : BaseFragment<SplashViewModel>(
    SplashViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkToken()
        observeSplashResultLiveData()
    }

    private fun observeSplashResultLiveData() {
        viewModel.splashResultLiveData.observe(viewLifecycleOwner) {
            when (it.consumeContent) {
                is Outcome.Success -> {
                    navigateTo(SplashFragmentDirections.toVoteFragment())
                }
                is Outcome.Failure -> {
                    navigateTo(SplashFragmentDirections.toLoginFragment())
                }
                else -> Unit
            }
        }
    }
}