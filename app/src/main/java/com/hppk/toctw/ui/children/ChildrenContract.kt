package com.hppk.toctw.ui.children

import com.hppk.toctw.data.model.Child

interface ChildrenContract {
    interface View {
        fun onChildrenLoaded(children: List<Child>)
    }

    interface Presenter {
        fun unsubscribe()
        fun getChildren()
    }
}