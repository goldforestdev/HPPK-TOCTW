package com.hppk.toctw.ui.settings


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_booth.*
import kotlinx.android.synthetic.main.fragment_developers.*
import kotlinx.android.synthetic.main.fragment_developers.toolbar


class DevelopersFragment : Fragment() {
    companion object {
        private const val RC_SIGN_IN = 2019
    }

    private val TAG = this::class.java.simpleName
    private var clickLYJ = 0
    private var clickKHJ = 0
    private var clickKYH = 0
    private var start = 0L
    private var end = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSignOutVisibility()

        initToolbar()

        cvSignOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(context!!)
                .addOnCompleteListener {
                    setSignOutVisibility()
                    initClick()
                }
        }

        cvYjLim.setOnClickListener {
            clickLYJ++
            checkAuthEnable()
        }

        cvHjKim.setOnClickListener {
            clickKHJ++
            checkAuthEnable()
        }

        cvYhKim.setOnClickListener {
            clickKYH++
            checkAuthEnable()
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
                actionBar.setTitle(R.string.developers)
            }
        }
    }

    private fun setSignOutVisibility() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            cvSignOut.visibility = View.VISIBLE
        } else {
            cvSignOut.visibility = View.INVISIBLE
        }
    }

    private fun checkAuthEnable() {
        if (start == 0L) {
            start = System.currentTimeMillis()
        }
        end = System.currentTimeMillis()
        if (clickLYJ >= 3 && clickKHJ >= 3 && clickKYH >= 3
            && FirebaseAuth.getInstance().currentUser == null
            && (end - start) <= 1000 * 3
        ) {
            initClick()
            showAuth()
        } else if (clickLYJ >= 3 && clickKHJ >= 3 && clickKYH >= 3) {
            initClick()
        }
    }

    private fun initClick() {
        clickLYJ = 0
        clickKHJ = 0
        clickKYH = 0
        start = 0L
        end = 0L
    }

    private fun showAuth(): Boolean {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult = $requestCode : $resultCode")
        if (requestCode == 2019) {
            if (resultCode == Activity.RESULT_OK) {
                setSignOutVisibility()
                val email = FirebaseAuth.getInstance().currentUser?.email
                Toast.makeText(context, "로그인 : $email", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
