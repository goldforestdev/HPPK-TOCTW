package com.hppk.toctw.ui.info


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionInflater
import com.google.android.material.appbar.AppBarLayout
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_info_schedule.*


class InfoScheduleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }


    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        var isShow = true
        var scrollRange = -1

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }

            if (scrollRange + verticalOffset == 0) {
                collapsingToolbarLayout.title = getString(R.string.info_schedule)
                isShow = true
            } else if(isShow) {
                collapsingToolbarLayout.title = " "
                isShow = false
            }
        })
    }

}
