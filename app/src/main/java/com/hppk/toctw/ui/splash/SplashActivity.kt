package com.hppk.toctw.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.auth.UserContract
import com.hppk.toctw.auth.UserPresenter
import com.hppk.toctw.data.model.User
import com.hppk.toctw.ui.MainActivity

class SplashActivity : AppCompatActivity(), UserContract.View {
    private val presenter: UserContract.Presenter by lazy { UserPresenter(this) }
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)
        if (AppAuth.isFirebaseLoginUser()) {
            val id = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            if (!TextUtils.isEmpty(id)) {
                presenter.findUser(id)
            } else {
                AppAuth.setUser(null)
                showSplashDelayed()
            }
        } else {
            AppAuth.setUser(null)
            showSplashDelayed()
        }
    }

    private fun showSplashDelayed() {
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

    override fun onFindUserError() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.d(TAG, "SignOut")
                AppAuth.setUser(null)
                showSplashDelayed()
            }
    }

    override fun onFindUserSuccess(user: User) {
        Log.d(TAG, "FindUser")
        showSplashDelayed()
    }

    override fun onAddUserError() {
    }

    override fun onAddUserSuccess(user: User) {
    }
}
