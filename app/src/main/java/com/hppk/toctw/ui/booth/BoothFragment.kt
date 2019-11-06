package com.hppk.toctw.ui.booth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.ui.details.BOOTH_INFO
import com.hppk.toctw.ui.details.BoothDetailsActivity
import kotlinx.android.synthetic.main.fragment_booth.*

class BoothFragment : Fragment(), BoothContract.View, BoothAdapter.BoothClickLister, BoothAdapter.BusyClicklister {

    private val presenter : BoothContract.Presenter by lazy { BoothPresenter(this) }

    private val boothAdapter: BoothAdapter by lazy { BoothAdapter(boothClickLister = this, busyClicklister = this) }

    companion object {
        private val TAG = BoothFragment::class.java.simpleName

        const val MAIN_WARNING_DLG = "MAIN_WARNING_DLG"
        const val MAIN_ACTIVITY_REMEMBER_ME_DLG = "MAIN_ACTIVITY_REMEMBER_ME_DLG"
    }
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
                actionBar.setTitle(R.string.booth)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onBoothListLoaded(boothDataList: List<Booth>) {
        boothAdapter.booths.addAll(boothDataList)
        boothAdapter.notifyDataSetChanged()
    }

    override fun onBoothClick(booth: Booth) {
        activity.apply {
            Toast.makeText(activity, "${booth.title} 상세 화면으로 이동 합니다.",Toast.LENGTH_LONG).show()
        }

        startActivity(
            Intent(context, BoothDetailsActivity::class.java).putExtra(BOOTH_INFO, booth)
        )
    }

    override fun onBusyClick(booth: Booth) {
        val boothStaffDialog = BoothStaffDialog()
        val bundle = Bundle()
        bundle.putParcelable(BOOTH_INFO, booth)
        boothStaffDialog.arguments = bundle
        boothStaffDialog.isCancelable = false
        boothStaffDialog.show(activity!!.supportFragmentManager,null)

        when {
            AppAuth.isAdmin -> Toast.makeText(activity, "나는 어디민이다.",Toast.LENGTH_LONG).show()
            AppAuth.isStaff -> Toast.makeText(activity, "나는 스텝이다.",Toast.LENGTH_LONG).show()
            else -> Toast.makeText(activity, "나는 어디민이 아니다.",Toast.LENGTH_LONG).show()
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
