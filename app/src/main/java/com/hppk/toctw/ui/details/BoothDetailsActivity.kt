package com.hppk.toctw.ui.details

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

        when (booth.floor) {
            Floor.FOUR -> cpLocation.setChipBackgroundColorResource(R.color.four_color)
            Floor.FIVE -> cpLocation.setChipBackgroundColorResource(R.color.five_color)
            Floor.SIX -> cpLocation.setChipBackgroundColorResource(R.color.six_color)
            Floor.SEVEN -> cpLocation.setChipBackgroundColorResource(R.color.seven_color)
            Floor.EIGHT -> cpLocation.setChipBackgroundColorResource(R.color.eight_color)
            Floor.NINE -> cpLocation.setChipBackgroundColorResource(R.color.nine_color)
            Floor.TEN -> cpLocation.setChipBackgroundColorResource(R.color.ten_color)
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
