package com.hppk.toctw.ui.info


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment() {

    private var isShowScheduleBody = false
    private var isShowLunchBody = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        val scheduleContentsBody =
            layoutInfoSchedule.findViewById<ConstraintLayout>(R.id.scheduleContentsBody)
        val lunchContentsBody =
            layoutInfoLunch.findViewById<ConstraintLayout>(R.id.lunchContentsBody)

        layoutInfoSchedule.setOnClickListener { toggleScheduleContentsBody(scheduleContentsBody) }
        layoutInfoLunch.setOnClickListener { toggleLunchContentsBody(lunchContentsBody) }
    }

    private fun toggleScheduleContentsBody(scheduleContentsBody: ConstraintLayout) {
        scheduleContentsBody.visibility = when {
            isShowScheduleBody -> View.GONE
            else -> View.VISIBLE
        }

        isShowScheduleBody = !isShowScheduleBody
    }

    private fun toggleLunchContentsBody(lunchContentsBody: ConstraintLayout) {
        lunchContentsBody.visibility = when {
            isShowLunchBody -> View.GONE
            else -> View.VISIBLE
        }

        isShowLunchBody = !isShowLunchBody
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

}
