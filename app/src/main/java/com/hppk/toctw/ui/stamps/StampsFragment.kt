package com.hppk.toctw.ui.stamps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Stamp
import kotlinx.android.synthetic.main.fragment_stamps.*

class StampsFragment : Fragment(), StampsContract.View {

    private val presenter: StampsContract.Presenter by lazy {
        StampsPresenter(this)
    }
    private val adapter: StampsAdapter by lazy { StampsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_stamps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter.getStamps()
    }

    private fun initView() {
        rvStamps.layoutManager = GridLayoutManager(context, 3)
        rvStamps.adapter = adapter

        fabStamp.isEnabled = true
        fabStamp.setOnClickListener { openCamera() }
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun onStampsLoaded(stamps: List<Stamp>) {
        adapter.stamps.clear()
        adapter.stamps.addAll(stamps)
        adapter.notifyDataSetChanged()
    }

    private fun openCamera() {
        fabStamp.isEnabled = false
    }
}
