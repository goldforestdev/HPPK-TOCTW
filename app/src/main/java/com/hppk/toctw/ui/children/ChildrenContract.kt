package com.hppk.toctw.ui.children

import com.hppk.toctw.data.model.Child

interface ChildrenContract {
    interface View {
        fun onChildrenLoaded(children: List<Child>)
        fun onChildSaved(child: Child)
    }

    interface Presenter {
        fun unsubscribe()
        fun getChildren()
        fun saveChild(name: String, avatarResId: Int)
        fun deleteChild(child: Child)
    }
}