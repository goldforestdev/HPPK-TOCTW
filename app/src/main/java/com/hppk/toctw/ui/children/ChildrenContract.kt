package com.hppk.toctw.ui.children

import com.hppk.toctw.data.model.Child

interface ChildrenContract {
    interface View {
        fun onChildrenLoaded(children: List<Child>)
        fun onEmptyChildrenLoaded()
        fun onChildDeleted()
    }

    interface Presenter {
        fun unsubscribe()
        fun getChildren()
        fun deleteChild(child: Child)
    }
}