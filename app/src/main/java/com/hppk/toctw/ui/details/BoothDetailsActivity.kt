package com.hppk.toctw.ui.details

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Floor
import kotlinx.android.synthetic.main.activity_booth_details.*
import kotlinx.android.synthetic.main.fragment_booth.toolbar


const val BOOTH_INFO = "boothInfo"
class BoothDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booth_details)

        if (!intent.hasExtra(BOOTH_INFO)) {
            finish()
            return
        }
        val booth = intent.getParcelableExtra<Booth>(BOOTH_INFO)
        initToolbar()
        initBoothInfo(booth)
        initBoothLocation(booth)
    }

    private fun initToolbar() {
          setSupportActionBar(toolbar)
          supportActionBar?.also { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
          }

        collapsingToolbarLayout.title = getString(R.string.program)
        collapsingToolbarLayout.setExpandedTitleColor(getColorWrapper())
    }

    private fun initBoothInfo(booth: Booth) {
        tvTitle.text = booth.title
        tvDetail.text = booth.description
    }

    private fun initBoothLocation(booth: Booth) {
        cpLocation.text = booth.location
        when (booth.floor) {
            Floor.FOUR -> {
                cpLocation.setChipBackgroundColorResource(R.color.four_color)
                ivFloor.setImageResource(R.drawable.four_floor)
            }
            Floor.FIVE -> {
                cpLocation.setChipBackgroundColorResource(R.color.five_color)
                ivFloor.visibility = View.GONE
            }
            Floor.SIX -> {
                cpLocation.setChipBackgroundColorResource(R.color.six_color)
                ivFloor.setImageResource(R.drawable.six_floor)
            }
            Floor.SEVEN -> {
                cpLocation.setChipBackgroundColorResource(R.color.seven_color)
                ivFloor.setImageResource(R.drawable.seven_floor)
            }
            Floor.EIGHT -> {
                cpLocation.setChipBackgroundColorResource(R.color.eight_color)
                ivFloor.setImageResource(R.drawable.eight_floor)
            }
            Floor.NINE -> {
                cpLocation.setChipBackgroundColorResource(R.color.nine_color)
                ivFloor.visibility = View.GONE
            }
            Floor.TEN -> {
                cpLocation.setChipBackgroundColorResource(R.color.ten_color)
                ivFloor.visibility = View.GONE
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getColorWrapper(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(android.R.color.holo_red_light)
        } else {
            resources.getColor(android.R.color.holo_red_light)
        }
    }
}
