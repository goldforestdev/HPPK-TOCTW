package com.hppk.toctw.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hppk.toctw.R
import com.hppk.toctw.ui.MainActivity

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val presenter : SplashContract.Presenter by lazy { SplashPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_spalsh)
        presenter.loadData()

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 500)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }


    override fun onError(errTitle: Int, errMsg: Int) {
        AlertDialog.Builder(this)
            .setTitle(errTitle)
            .setMessage(errMsg)
            .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
            .setOnDismissListener { finish() }
            .show()
    }

}
