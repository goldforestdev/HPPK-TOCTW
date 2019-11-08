package com.hppk.toctw.ui.home


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Notice
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), NoticeContract.View, HomeAdapter.ClickLister {
    private val presenter: NoticeContract.Presenter by lazy { NoticePresenter(this) }
    private val noticeAdapter: HomeAdapter by lazy { HomeAdapter(clickLister = this) }
    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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
                actionBar.setTitle(R.string.home)
            }
        }
    }

    private fun initData() {
        presenter.loadCollection()
    }

    private fun initRecyclerView() {
        rc_notice.layoutManager = LinearLayoutManager(activity)
        rc_notice.adapter = noticeAdapter
        noticeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    override fun onError() {
        Log.d(TAG, "Notice Load Error")
    }

    override fun onNoticeListLoaded(noticeDataList: List<Notice>) {
        noticeAdapter.notices.clear()
        noticeAdapter.notices.addAll(noticeDataList)
        noticeAdapter.notifyDataSetChanged()
    }

    override fun onAddNoticeSuccess(notice: Notice) {
    }

    override fun onAddNoticeClick() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddNoticeFragment())
    }

    override fun onShowBoothClick() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBoothFragment())
    }

    override fun onShowYouTube() {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:Pw1K4wJwCp8"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=Pw1K4wJwCp8")
        )
        activity?.apply {
            try {
                this.startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                this.startActivity(webIntent)
            }
        }
    }
}
