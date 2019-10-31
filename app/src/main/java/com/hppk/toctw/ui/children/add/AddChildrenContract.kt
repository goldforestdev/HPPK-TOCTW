package com.hppk.toctw.ui.children.add

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender

interface AddChildrenContract {
    interface View {
        fun onChildAdded(child: Child)
        fun clearEditText()
    }

    interface Presenter {
        fun unsubscribe()
        fun saveChild(name: String, gender: Gender)
    }
}