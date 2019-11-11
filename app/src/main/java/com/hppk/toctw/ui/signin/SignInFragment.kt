package com.hppk.toctw.ui.signin


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_sign_in.*

private const val RC_SIGN_IN = 2019
private const val URL_TOS = "https://sites.google.com/view/toctw2019-toc/%ED%99%88?authuser=2"
private const val URL_PP = "https://sites.google.com/view/toctw-privacy/%ED%99%88?authuser=2"

class SignInFragment : Fragment(), SignInContract.View {

    private val presenter: SignInContract.Presenter by lazy { SignInPresenter(this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initTosPP()

        btnSignIn.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_close)
                actionBar.title = ""
            }
        }
    }

    private fun initTosPP() {
        val span = SpannableStringBuilder(getString(R.string.tos_pp))
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showWeb(URL_TOS)
            }
        }, 33, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showWeb(URL_PP)
            }
        }, 44, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvTosPP.text = span
        tvTosPP.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun showWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.saveMe()
            } else {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSignInDone() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }
}
