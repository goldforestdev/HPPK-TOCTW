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
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.auth.UserContract
import com.hppk.toctw.auth.UserPresenter
import com.hppk.toctw.data.model.User
import kotlinx.android.synthetic.main.fragment_developers.*


class DevelopersFragment : Fragment(), UserContract.View {
    companion object {
        private const val RC_SIGN_IN = 2019
    }

    private val TAG = this::class.java.simpleName
    private var clickLYJ = 0
    private var clickKHJ = 0
    private var clickKYH = 0
    private var start = 0L
    private var end = 0L

    private val presenter: UserContract.Presenter by lazy { UserPresenter(this) }

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
                    AppAuth.setUser(null)
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

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
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
        if (AppAuth.isAdmin() || AppAuth.isStaff()) {
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
            && AppAuth.getUser() == null
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
                presenter.findUser()
            } else {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFindUserError() {
        Log.d(TAG, "FindUser Error")
        presenter.addUser()
    }

    override fun onFindUserSuccess(user: User) {
        Toast.makeText(context, "로그인 : ${AppAuth.getUser()?.email}", Toast.LENGTH_SHORT).show()
        setSignOutVisibility()
    }

    override fun onAddUserError() {
        Log.d(TAG, "AddUser Error")
    }

    override fun onAddUserSuccess(user: User) {
        Log.d(TAG, "AddUser")
        Toast.makeText(context, "로그인 : ${AppAuth.getUser()?.email}", Toast.LENGTH_SHORT).show()
        setSignOutVisibility()
    }
}
