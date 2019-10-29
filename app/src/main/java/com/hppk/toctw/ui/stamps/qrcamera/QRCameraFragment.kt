package com.hppk.toctw.ui.stamps.qrcamera


import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_qrcamera.*

class QRCameraFragment : Fragment(), QRCodeAnalyzer.QRCodeFoundListener {

    private lateinit var qrAnalysis: ImageAnalysis
    private val imageAnalysisConfig: ImageAnalysisConfig by lazy {
        ImageAnalysisConfig.Builder()
            .setTargetResolution(Size(1280, 720))
            .build()
    }
    private val previewConfig: PreviewConfig by lazy {
        PreviewConfig.Builder().apply {
            val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
            val screenSize = Size(metrics.widthPixels, metrics.heightPixels)
            val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

            setTargetResolution(screenSize)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(viewFinder.display.rotation)
        }.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_qrcamera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewFinder.post { startCamera() }
    }

    private fun startCamera() {
        qrAnalysis = ImageAnalysis(imageAnalysisConfig).apply {
            analyzer = QRCodeAnalyzer(this@QRCameraFragment)
        }

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = previewOutput.surfaceTexture
        }

        CameraX.bindToLifecycle(this as LifecycleOwner, preview, qrAnalysis)
    }

    override fun onQRCodeFound(data: String) {
        Toast.makeText(activity?.applicationContext, "QR Code: $data", Toast.LENGTH_SHORT).show()
        aniDone.visibility = View.VISIBLE
        aniDone.playAnimation()

        CameraX.unbind(qrAnalysis)
    }

}
