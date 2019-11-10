package com.hppk.toctw.ui.stamps.qrcamera

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata


private const val QR_CODE_KEY_BOOTH_ID = "boothId:"

class QRCodeAnalyzer(
    private val qrCodeFoundListener: QRCodeFoundListener
) : ImageAnalysis.Analyzer {

    private val TAG = QRCodeAnalyzer::class.java.simpleName

    interface QRCodeFoundListener {
        fun onQRCodeFound(boothId: String)
    }

    private val detectorOptions: FirebaseVisionBarcodeDetectorOptions by lazy {
        FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy?, degrees: Int) {
        imageProxy?.image?.let { mediaImage ->
            val image =
                FirebaseVisionImage.fromMediaImage(mediaImage, degreesToFirebaseRotation(degrees))

            FirebaseVision.getInstance()
                .getVisionBarcodeDetector(detectorOptions)
                .detectInImage(image)
                .addOnFailureListener { Log.e(TAG, "[TOCTW] analyze - failed: ${it.message}", it) }
                .addOnSuccessListener { qrCodeList ->
                    Log.d(TAG, "[TOCTW] analyze - success: ${qrCodeList.size}")
                    qrCodeList.firstOrNull {
                        it.rawValue?.contains(QR_CODE_KEY_BOOTH_ID) ?: false
                    }?.let { qrCode ->
                        qrCode.rawValue?.let { data ->
                            qrCodeFoundListener.onQRCodeFound(data.replace(QR_CODE_KEY_BOOTH_ID, "").trim())
                            return@addOnSuccessListener
                        }
                    }
                }
        }
    }
}