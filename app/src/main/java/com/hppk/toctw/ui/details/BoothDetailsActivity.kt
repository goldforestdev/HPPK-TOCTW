package com.hppk.toctw.ui.details

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
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
        collapsingToolbarLayout.setExpandedTitleColor(getColorWrapper(this,  android.R.color.holo_red_light))
    }

    private fun initBoothInfo(booth: Booth) {
        tvTitle.text = booth.title
        tvDetail.text = booth.description
    }

    private fun initBoothLocation(booth: Booth) {
        cpLocation.text = booth.location

        if (booth.locationRes.isNotEmpty()) {
            val id = resources.getIdentifier(booth.locationRes, "drawable", packageName)
            val drawable = resources.getDrawable(id)
            Glide.with(this).load(drawable).into(ivFloor)
        } else {
            ivFloor.visibility = View.GONE
        }

        if (booth.locationColorRes.isNotEmpty()) {
            val id = resources.getIdentifier(booth.locationColorRes, "color", packageName)
            cpLocation.setChipBackgroundColorResource(id)
        } else {
            cpLocation.setCheckedIconResource(R.color.five_color)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getColorWrapper(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }
}
