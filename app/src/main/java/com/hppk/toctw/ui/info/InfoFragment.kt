package com.hppk.toctw.ui.info


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.hppk.toctw.BuildConfig
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_info_contact.*
import kotlinx.android.synthetic.main.layout_info_lunch.*
import kotlinx.android.synthetic.main.layout_info_schedule.*
import kotlinx.android.synthetic.main.layout_info_security_safety.*


class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        layoutInfoSchedule.setOnClickListener {
            val extras = FragmentNavigatorExtras(ivSchedule to "transitionSchedule")
            findNavController().navigate(R.id.action_infoFragment_to_infoScheduleFragment, null, null, extras)
        }
        layoutInfoLunch.setOnClickListener {
            val extras = FragmentNavigatorExtras(ivLunch to "transitionLunch")
            findNavController().navigate(R.id.action_infoFragment_to_infoLunhFragment, null, null, extras)
        }

        tvSecuritySafetyDetails.text = Html.fromHtml(getString(R.string.security_env_details))

        btnCallPark.setOnClickListener { call(BuildConfig.PHONE_NUMBER_PARK) }
        btnCallLee.setOnClickListener { call(BuildConfig.PHONE_NUMBER_LEE) }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
            }
        }
    }

    private fun call(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)

    }
}
