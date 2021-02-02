package com.soa.utils

import android.app.AlertDialog
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.soa.R
import com.soa.data.remote.AppConstants.GENERIC_ERROR_MESSAGE
import com.soa.data.remote.AppConstants.SERVER_ERROR_MESSAGE
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) =
    observe(lifecycleOwner) { it?.let(action) }


suspend fun <T> handleApiResponse(
    responseFunction: suspend () -> Response<T>,
    onSuccess: (T?) -> Unit = {},
    onFailure: (String) -> Unit = {}
) {
    try {
        val response = responseFunction()

        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            onFailure(response.errorBody()?.string()?.trim('"') ?: GENERIC_ERROR_MESSAGE)
        }
    } catch (e: Exception) {
        if (e is ConnectException || e is SocketException) {
            onFailure(SERVER_ERROR_MESSAGE)
        }
    }
}

fun Fragment.navigateTo(action: NavDirections) = findNavController().navigate(action)

fun Fragment.showCancellableAlertDialog(message: String, onDismiss: () -> Unit = {}) {
    AlertDialog.Builder(activity)
        .setMessage(message)
        .setPositiveButton(R.string.soa_ok) { dialog, _ ->
            dialog.dismiss()
            onDismiss()
        }
        .create()
        .show()
}