package com.hppk.toctw.ui.booth

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.hppk.toctw.R

class BoothStaffDialog  : DialogFragment(){

    override fun onStart() {
        super.onStart()
        var dialogWidth = resources.getDimensionPixelSize(R.dimen.dialog_size)

        val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window!!.setLayout(dialogWidth, dialogHeight)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        return inflater.inflate(R.layout.dialog_staff_booth, container, false)
    }

}