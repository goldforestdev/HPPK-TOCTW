package com.hppk.toctw.ui.stamps.qrcamera


import android.animation.Animator
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
import androidx.navigation.fragment.findNavController
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_qrcamera.*

class QRCameraFragment : Fragment(), QRCodeAnalyzer.QRCodeFoundListener, QRCodeContract.View {

    private lateinit var qrAnalysis: ImageAnalysis

    private val presenter: QRCodeContract.Presenter by lazy { QRCodePresenter(this) }
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

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
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

    override fun onQRCodeFound(boothId: String) {
        CameraX.unbind(qrAnalysis)
        aniDone.visibility = View.VISIBLE
        aniDone.playAnimation()

        aniDone.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                presenter.saveStamp(boothId)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    override fun onStampSaved() {
        fragmentManager?.popBackStackImmediate()
    }

}
