package com.hppk.toctw.ui.booth

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Busy
import com.hppk.toctw.data.model.Floor
import com.hppk.toctw.ui.details.BOOTH_INFO
import kotlinx.android.synthetic.main.dialog_staff_booth.*

class BoothStaffDialog(
    private val boothBusyStatusClickListener: BoothBusyStatusClickListener
)  : DialogFragment(){

    private var booth : Booth? = null
    private var currentBusy : Busy = Busy.GOOD

    interface BoothBusyStatusClickListener {
        fun busyState(booth: Booth)
    }

    override fun onStart() {
        super.onStart()
        val dialogWidth = resources.getDimensionPixelSize(R.dimen.dialog_size)
        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window!!.setLayout(dialogWidth, dialogHeight)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        return inflater.inflate(R.layout.dialog_staff_booth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        booth = arguments?.getParcelable(BOOTH_INFO)
        booth?.apply {
            tvTitle.text = this.title
            when (busy) {
                Busy.GOOD -> rbGood.isChecked = true
                Busy.NORMAL -> rbNormal.isChecked = true
                Busy.VERY_BUSY -> rbBusy.isChecked = true
                Busy.CLOSE -> rbClose.isChecked = true
            }
        }

        initRadioGroup()
        btnOk.setOnClickListener {
            booth?.apply {
                this.busy = currentBusy
                boothBusyStatusClickListener.busyState(this)
            }

            dismissAllowingStateLoss()
        }
    }

    private fun initRadioGroup() {
        rgBusy.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rbGood -> {
                    rbGood.isChecked = true
                    currentBusy = Busy.GOOD
                }
                R.id.rbNormal -> {
                    rbNormal.isChecked = true
                    currentBusy = Busy.NORMAL
                }

                R.id.rbBusy -> {
                    rbBusy.isChecked = true
                    currentBusy = Busy.VERY_BUSY
                }
                R.id.rbClose-> {
                    rbClose.isChecked = true
                    currentBusy = Busy.CLOSE
                }
            }
        }
    }

}