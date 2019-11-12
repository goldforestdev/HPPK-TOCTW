package com.hppk.toctw.ui.stamps.qrcamera


import android.animation.Animator
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hppk.toctw.R
import com.hppk.toctw.data.repository.StampRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_qrcamera.*

class QRCameraFragment : Fragment(), QRCodeAnalyzer.QRCodeFoundListener, QRCodeContract.View {

    private lateinit var qrAnalysis: ImageAnalysis

    private val args: QRCameraFragmentArgs by navArgs()
    private val presenter: QRCodeContract.Presenter by lazy {
        val db = AppDatabase.getInstance(context!!)
        QRCodePresenter(
            this,
            StampRepository(db.stampDao(), db.childStampDao())
        )
    }
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
        qrAnalysis.removeAnalyzer()
        CameraX.unbind(qrAnalysis)

        if (aniDone != null && aniDone.visibility != View.VISIBLE) {
            aniDone.visibility = View.VISIBLE
            aniDone.playAnimation()

            aniDone.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    presenter.saveStamp(args.child, boothId)
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
        }
    }

    override fun onStampSaved() {
        findNavController().navigateUp()
    }

}
