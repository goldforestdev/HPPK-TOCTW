package com.hppk.toctw.ui.children


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_children.*


class ChildrenFragment : Fragment(), ChildrenContract.View {

    private val presenter: ChildrenContract.Presenter by lazy {
        val childDao = AppDatabase.getInstance(context!!).childrenDao()
        ChildrenPresenter(this, ChildrenRepository(childDao))
    }
    private val adapter: ChildrenAdapter by lazy { ChildrenAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_children, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val helper = LinearSnapHelper()
        helper.attachToRecyclerView(rvChildren)

        rvChildren.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvChildren.adapter = adapter
        fabAddChild.setOnClickListener { moveToAddChildView() }

        presenter.getChildren()
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        super.onDestroyView()
    }

    override fun moveToAddChildView() {
        findNavController().navigate(ChildrenFragmentDirections.actionSelectChildFragmentToAddChildrenFragment())
    }

    override fun onChildrenLoaded(children: List<Child>) {
        adapter.children.addAll(children)
        adapter.notifyDataSetChanged()
    }

}
