package com.soa.page.vote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.children
import androidx.lifecycle.observe
import com.soa.R
import com.soa.base.BaseFragment
import com.soa.data.model.Answer
import com.soa.data.model.Question
import com.soa.utils.Outcome
import com.soa.utils.navigateTo
import com.soa.utils.showCancellableAlertDialog
import kotlinx.android.synthetic.main.fragment_vote.*
import timber.log.Timber

@SuppressLint("SetTextI18n")
class VoteFragment : BaseFragment<VoteViewModel>(VoteViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vote, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        viewModel.loadQuestion()
        observeQuestionResultLiveData()
        observeVoteResultLiveData()
    }

    private fun initLayout() {
        userNameTextView.text = viewModel.userName
        logoutButton.setOnClickListener {
            viewModel.logout()
            navigateTo(VoteFragmentDirections.toLoginFragment())
        }
    }

    private fun initQuestionLayout(question: Question) {
        voteQuestionTextView.text = question.text
        voteRadioGroup.removeAllViews()
        question.answers.forEach {
            val radioButton = RadioButton(requireContext())
            radioButton.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(0, 12, 0, 12)
            }
            radioButton.text = " ${it.text} (${it.votes})"
            radioButton.textSize = 28f
            radioButton.id = it.id.toInt()
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.vote(it.id)
                }
            }
            voteRadioGroup.addView(radioButton)
        }
    }

    private fun updateAnswers(answers: List<Answer>) {
        voteRadioGroup.children.forEach { child ->
            val radioButton = child as RadioButton
            answers.find { radioButton.id == it.id.toInt() }?.let { answer ->
                radioButton.text = " ${answer.text} (${answer.votes})"
            }
        }
    }

    private fun observeQuestionResultLiveData() {
        viewModel.questionResultLiveData.observe(viewLifecycleOwner) {
            when (val result = it.consumeContent) {
                is Outcome.Progress -> {
                    Timber.i("getting question")
                    voteOverlay.showLoading(R.string.soa_loading_vote)
                }
                is Outcome.Success -> {
                    Timber.i("got answer: ${result.data}")
                    voteOverlay.hideLoading()
                    initQuestionLayout(result.data)
                }
                is Outcome.Failure -> {
                    Timber.e("login failed: ${result.errorMessage}")
                    voteOverlay.hideLoading()
                    showCancellableAlertDialog("Could not get question. Please try again.")
                }
            }
        }
    }

    private fun observeVoteResultLiveData() {
        viewModel.voteResultLiveData.observe(viewLifecycleOwner) {
            when (val result = it.consumeContent) {
                is Outcome.Progress -> {
//                    Timber.i("getting vote response")
//                    voteOverlay.showLoading(R.string.soa_loading_question)
                }
                is Outcome.Success -> {
                    Timber.i("got vote: ${result.data}")
                    voteOverlay.hideLoading()
                    updateAnswers(result.data)
                }
                is Outcome.Failure -> {
                    Timber.e("login failed: ${result.errorMessage}")
                    voteOverlay.hideLoading()
                    showCancellableAlertDialog("Could not get question. Please try again.")
                }
            }
        }
    }
}