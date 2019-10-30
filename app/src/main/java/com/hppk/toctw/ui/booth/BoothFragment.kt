package com.hppk.toctw.ui.booth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
import kotlinx.android.synthetic.main.fragment_booth.*

class BoothFragment : Fragment(), BoothContract.View, BoothAdapter.BoothClickLister {

    private val presenter : BoothContract.Presenter by lazy { BoothPresenter(this) }

    private val boothAdapter: BoothAdapter by lazy { BoothAdapter(boothClickLister = this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecyclerView()
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

    }

}
