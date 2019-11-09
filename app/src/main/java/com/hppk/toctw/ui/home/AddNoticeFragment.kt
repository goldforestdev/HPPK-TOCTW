package com.hppk.toctw.ui.home


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Notice
import kotlinx.android.synthetic.main.fragment_add_notice_fragement.*
import kotlinx.android.synthetic.main.fragment_booth.toolbar

class AddNoticeFragment : Fragment(), NoticeContract.View {
    private val presenter: NoticeContract.Presenter by lazy { NoticePresenter(this) }
    private val TAG = this::class.java.simpleName
    private var titleSize = 0
    private var bodySize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_notice_fragement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
                actionBar.setTitle(R.string.add_notice)
            }
        }
    }

    private fun initView() {
        bt_send.isEnabled = false

        et_notice_title.addTextChangedListener {
            titleSize = it?.length ?: 0
            checkSendButtonVisibility()
        }

        et_notice_body.addTextChangedListener {
            bodySize = it?.length ?: 0
            checkSendButtonVisibility()
        }

        bt_send.setOnClickListener {
            hideKeyboard()
            bt_send.isEnabled = false
            val notice = Notice(et_notice_title.text.toString(), et_notice_body.text.toString())
            presenter.addNotice(notice)
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity?.currentFocus != null)
            inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun checkSendButtonVisibility() {
        bt_send.isEnabled = titleSize > 0 && bodySize > 0
    }

    override fun onError() {
        Log.d(TAG, "Notice Add Error")
    }

    override fun onNoticeListLoaded(noticeDataList: List<Notice>) {
    }

    override fun onAddNoticeSuccess(notice: Notice) {
        clearEditText()
        findNavController().popBackStack()
    }

    private fun clearEditText() {
        et_notice_title.setText("")
        et_notice_body.setText("")
        checkSendButtonVisibility()
    }
}
