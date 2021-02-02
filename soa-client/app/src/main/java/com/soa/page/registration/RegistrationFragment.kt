//package com.soa.page.registration
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Matrix
//import android.graphics.Paint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.soa.R
//import com.soa.base.BaseFragment
//import com.soa.utils.Outcome
//import com.soa.utils.observeNonNull
//import com.soa.utils.remove
//import com.soa.utils.showCancellableAlertDialog
//import kotlinx.android.synthetic.main.fragment_registration.*
//import timber.log.Timber
//import java.io.File
//
//
//class RegistrationFragment : BaseFragment<RegistrationViewModel>(RegistrationViewModel::class) {
//
//    private val args: RegistrationFragmentArgs by navArgs()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? = inflater.inflate(R.layout.fragment_registration, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        args.face?.imageFile?.let { image ->
//            showImage(image)
//            setRegisterButtonClickListener(image)
//            observeResultLiveData()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        args.face?.imageFile?.remove()
//    }
//
//    private fun showImage(image: File) {
//        args.face?.faceRectangle?.let { rect ->
//            val bitmap = BitmapFactory.decodeFile(image.path)
//            val rotatedBitmap =
//                Bitmap.createBitmap(
//                    bitmap,
//                    BITMAP_X_COORDINATE,
//                    BITMAP_Y_COORDINATE,
//                    bitmap.width,
//                    bitmap.height,
//                    Matrix().apply { postRotate(90f) },
//                    true
//                )
//            bitmap.recycle()
//
//            val canvas = Canvas(rotatedBitmap)
//            val paint = Paint().apply {
//                style = Paint.Style.STROKE
//                strokeWidth = RECT_PAINT_WIDTH
//                color = Color.RED
//            }
//
//            canvas.drawRect(rect, paint)
//            takenPhotoImageView.setImageBitmap(rotatedBitmap)
//            takenPhotoImageView.draw(canvas)
//        }
//    }
//
//    private fun setRegisterButtonClickListener(image: File) {
//        registerButton.setOnClickListener {
//            if (nameEditText.text.isNullOrEmpty()) {
//                nameEditText.error = getString(R.string.soa_name_validation_text)
//            } else {
//                viewModel.registerUser(nameEditText.text.toString().trim(), image)
//            }
//        }
//    }
//
//    private fun observeResultLiveData() {
//        viewModel.registrationResultLiveData.observeNonNull(viewLifecycleOwner) {
//            when (val result = it.consumeContent) {
//                is Outcome.Progress -> {
//                    Timber.i("registration in progress")
//                    registrationOverlay.showLoading(R.string.soa_registering_new_face)
//                }
//                is Outcome.Success -> {
//                    Timber.i("registration succeeded: ${result.data}")
//                    registrationOverlay.hideLoading()
//                    showCancellableAlertDialog(
//                        getString(
//                            R.string.soa_alert_registered_new_face,
//                            nameEditText.text.toString().trim()
//                        )
//                    ) {
//                        findNavController().popBackStack()
//                    }
//                }
//                is Outcome.Failure -> {
//                    Timber.e("registration failed: ${result.errorMessage}")
//                    registrationOverlay.hideLoading()
//                    showCancellableAlertDialog(result.errorMessage)
//                }
//            }
//        }
//    }
//
//    private companion object {
//        const val BITMAP_X_COORDINATE = 0
//        const val BITMAP_Y_COORDINATE = 0
//        const val RECT_PAINT_WIDTH = 5f
//    }
//}