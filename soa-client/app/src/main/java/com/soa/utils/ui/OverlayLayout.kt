package com.soa.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.soa.R
import com.soa.data.remote.AppConstants.EMPTY_STRING
import kotlinx.android.synthetic.main.layout_overlay.view.*

class OverlayLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_overlay, this, true)
    }

    fun showLoading(loadingMessage: String = EMPTY_STRING) {
        visibility = View.VISIBLE
        overlayLayoutTextView.text = loadingMessage
    }

    fun hideLoading() {
        visibility = View.GONE
        overlayLayoutTextView.text = EMPTY_STRING
    }

    fun showLoading(loadingMessageResId: Int) =
        showLoading(resources.getString(loadingMessageResId))
}