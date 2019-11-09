package com.hppk.toctw.ui.booth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.ui.details.BOOTH_INFO
import com.hppk.toctw.ui.details.BoothDetailsActivity
import com.hppk.toctw.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_booth.*

class BoothFragment : Fragment(), BoothContract.View, BoothAdapter.BoothClickLister
    , BoothAdapter.BusyClickLister, BoothStaffDialog.BoothBusyStatusClickListener, BoothAdapter.StampClickLister {

    private val presenter : BoothContract.Presenter by lazy { BoothPresenter(this) }
    private val boothAdapter: BoothAdapter by lazy { BoothAdapter(boothClickLister = this, busyClickLister = this, stampClickLister = this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initData()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
                actionBar.setTitle(R.string.program)
            }
        }
    }

    private fun initData () {
        presenter.loadCollection()
    }

    private fun initRecyclerView(){
        rcBoothList.layoutManager = LinearLayoutManager(activity)
        rcBoothList.adapter = boothAdapter
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onError(errTitle: Int, errMsg: Int) {

    }
    override fun onBoothListLoaded(boothDataList: List<Booth>) {
        boothAdapter.booths.addAll(boothDataList)
        boothAdapter.notifyDataSetChanged()
    }

    override fun onBoothClick(booth: Booth) {

        startActivity(
            Intent(context, BoothDetailsActivity::class.java).putExtra(BOOTH_INFO, booth)
        )
    }

    override fun onStampClick(booth: Booth) {
        findNavController().navigate(BoothFragmentDirections.actionBoothFragmentToChildrenFragment())
    }

    override fun busyState(booth: Booth) {
        presenter.updateBoothInfo(booth)
    }


    override fun onUpdateBoothInfoSuccess() {
        boothAdapter.notifyDataSetChanged()
    }

    override fun onBusyClick(booth: Booth) {

        if (AppAuth.isStaff) {
            val boothStaffDialog = BoothStaffDialog(this)
            val bundle = Bundle()
            bundle.putParcelable(BOOTH_INFO, booth)
            boothStaffDialog.arguments = bundle
            boothStaffDialog.isCancelable = false
            boothStaffDialog.show(activity!!.supportFragmentManager,null)
        }

    }

    override fun showWaitingView(show: Boolean)  {
        if (show) {
            rcBoothList.visibility = View.GONE
            spin_kit.visibility = View.VISIBLE
        } else {
            rcBoothList.visibility = View.VISIBLE
            spin_kit.visibility = View.GONE
        }
    }

}
