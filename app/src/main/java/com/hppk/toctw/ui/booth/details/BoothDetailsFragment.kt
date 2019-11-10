package com.hppk.toctw.ui.booth.details


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
import kotlinx.android.synthetic.main.fragment_booth_details.*


class BoothDetailsFragment : Fragment() {

    private val args: BoothDetailsFragmentArgs by navArgs()
    private val staffsAdapter: StaffsAdapter by lazy { StaffsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_booth_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initBoothInfo(args.booth)
        initBoothLocation(args.booth)
        initRecyclerView(args.booth)
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
                collapsingToolbarLayout.title = getString(R.string.program)
                isShow = true
            } else if (isShow) {
                collapsingToolbarLayout.title = " "
                isShow = false
            }
        })
    }

    private fun initRecyclerView(booth: Booth) {
        rcStaffs.layoutManager = LinearLayoutManager(context)
        rcStaffs.adapter = staffsAdapter
        staffsAdapter.staffs.addAll(booth.members)
        staffsAdapter.notifyDataSetChanged()
    }

    private fun initBoothInfo(booth: Booth) {
        tvTitle.text = booth.title
        tvDetail.text = booth.description
    }

    private fun initBoothLocation(booth: Booth) {
        cpLocation.text = booth.location

        if (booth.locationRes.isNotEmpty()) {
            val id = resources.getIdentifier(booth.locationRes, "drawable", activity?.packageName)
            val drawable = resources.getDrawable(id)
            ivFloor.setImageDrawable(drawable)
        } else {
            ivFloor.visibility = View.GONE
        }

        if (booth.locationColorRes.isNotEmpty()) {
            var id = resources.getIdentifier(booth.locationColorRes, "color", activity?.packageName)
            if (id == 0) {
                id = getColorWrapper(context!!, R.color.five_color)
            }
            cpLocation.setChipBackgroundColorResource(id)
        } else {
            cpLocation.setCheckedIconResource(R.color.five_color)
        }
    }

    private fun getColorWrapper(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }

}
