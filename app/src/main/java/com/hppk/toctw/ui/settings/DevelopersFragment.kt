package com.hppk.toctw.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.R
import com.hppk.toctw.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_developers.*


class DevelopersFragment : Fragment() {
    private var clickLYJ = false
    private var clickKHJ = false
    private var clickKYH = false

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

        cvSignOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(context!!)
                .addOnCompleteListener {
                    setSignOutVisibility()
                    initClick()
                }
        }

        cvYjLim.setOnClickListener {
            clickLYJ = true
            checkAuthEnable()
        }

        cvHjKim.setOnClickListener {
            clickKHJ = true
            checkAuthEnable()
        }

        cvYhKim.setOnClickListener {
            clickKYH = true
            checkAuthEnable()
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
        if (clickLYJ && clickKHJ && clickKYH && FirebaseAuth.getInstance().currentUser == null) {
            initClick()
            showAuth()
        }
    }

    private fun initClick() {
        clickLYJ = false
        clickKHJ = false
        clickKYH = false
    }

    private fun showAuth(): Boolean {
        //findNavController().navigate(DevelopersFragmentDirections.actionDevelopersFragmentToAuthFragment())
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        activity?.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            MainActivity.RC_SIGN_IN)
        return true
    }
}
