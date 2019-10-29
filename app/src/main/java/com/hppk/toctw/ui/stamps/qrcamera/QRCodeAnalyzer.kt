package com.hppk.toctw.ui.stamps.qrcamera

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata


class QRCodeAnalyzer(
    private val qrCodeFoundListener: QRCodeFoundListener
) : ImageAnalysis.Analyzer {

    private val TAG = QRCodeAnalyzer::class.java.simpleName

    interface QRCodeFoundListener {
        fun onQRCodeFound(data: String)
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
                .addOnSuccessListener { qrCodeList ->
                    Log.d(TAG, "[TOCTW] analyze - barcodes size: ${qrCodeList.size}")
                    qrCodeList.firstOrNull()?.rawValue?.let { data ->
                        Log.d(TAG, "[TOCTW] analyze - rawValue: $data")
                        qrCodeFoundListener.onQRCodeFound(data)
                        return@addOnSuccessListener
                    }
                }
                .addOnFailureListener { t ->
                    Log.e(TAG, "[TOCTW] analyze - failed: ${t.message}", t)
                }
        }
    }
}