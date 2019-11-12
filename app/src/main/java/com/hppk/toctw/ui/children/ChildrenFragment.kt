package com.hppk.toctw.ui.children


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_children.*


class ChildrenFragment : Fragment(), ChildrenContract.View, ChildrenAdapter.ChildClickListener {

    private val presenter: ChildrenContract.Presenter by lazy {
        val childDao = AppDatabase.getInstance(context!!).childrenDao()
        ChildrenPresenter(this, ChildrenRepository(childDao))
    }
    private lateinit var adapter: ChildrenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_children, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(rvChildren)

        adapter = ChildrenAdapter(context!!, childListener = this)

        rvChildren.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvChildren.adapter = adapter

        presenter.getChildren()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_child, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menuAddChild -> {
            showAddChildView()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        super.onDestroyView()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
                actionBar.setTitle(R.string.my_children)
            }
        }
    }

    override fun onEmptyChildrenLoaded() {
        rvChildren.visibility = View.GONE
        tvEmptyChild.visibility = View.VISIBLE
    }

    override fun onChildrenLoaded(children: List<Child>) {
        tvEmptyChild.visibility = View.GONE
        rvChildren.visibility = View.VISIBLE

        adapter.viewWidth = view?.width ?: 1080

        adapter.children.clear()
        adapter.children.addAll(children)
        adapter.notifyDataSetChanged()
    }

    private fun showAddChildView() {
        findNavController().navigate(ChildrenFragmentDirections.actionChildrenFragmentToAddChildFragment())
    }

    override fun onChildClicked(imageView: ImageView, child: Child) {
        val extras = FragmentNavigatorExtras(imageView to "avatar")
        val bundle = Bundle().apply { putParcelable("child", child) }
        findNavController().navigate(
            R.id.action_selectChildFragment_to_stampsFragment,
            bundle,
            null,
            extras
        )
    }

    override fun deleteChild(child: Child) {
        AlertDialog.Builder(context!!)
            .setMessage(R.string.confirm_delete_child)
            .setPositiveButton(R.string.delete) { _, _ ->
                val position = adapter.children.indexOf(child)
                adapter.children.remove(child)
                adapter.notifyItemRemoved(position)
                presenter.deleteChild(child)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    override fun onChildDeleted() {
        if(adapter.children.isEmpty()) {
            onEmptyChildrenLoaded()
        }
    }

}
