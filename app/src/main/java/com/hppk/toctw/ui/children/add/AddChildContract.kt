package com.hppk.toctw.ui.children.add

import com.hppk.toctw.data.model.Child

interface AddChildContract {
    interface View {
        fun onChildSaved(child: Child)
    }

    interface Presenter {
        fun unsubscribe()
        fun saveChild(childName: String, avatarResId: Int)
    }
}