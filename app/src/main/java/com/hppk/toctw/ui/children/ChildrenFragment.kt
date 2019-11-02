package com.hppk.toctw.ui.children


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_booth.*
import kotlinx.android.synthetic.main.fragment_children.*
import kotlinx.android.synthetic.main.fragment_children.toolbar


class SharedViewModel : ViewModel() {
    val avatarResId = MutableLiveData<Int>()
}

class ChildrenFragment : Fragment(), ChildrenContract.View, ChildrenAdapter.ChildClickListener {

    private val presenter: ChildrenContract.Presenter by lazy {
        val childDao = AppDatabase.getInstance(context!!).childrenDao()
        ChildrenPresenter(this, ChildrenRepository(childDao))
    }
    private val adapter: ChildrenAdapter by lazy { ChildrenAdapter(childListener = this) }

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        model.avatarResId.observe(this, Observer<Int> { avatarResId ->
            if (avatarResId != 0) {
                adapter.children.last().avatar = avatarResId
                adapter.notifyItemChanged(adapter.children.size - 1)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_children, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        val helper = LinearSnapHelper()
        helper.attachToRecyclerView(rvChildren)

        rvChildren.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvChildren.adapter = adapter

        presenter.getChildren()
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        model.avatarResId.value = 0
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

    override fun onChildrenLoaded(children: List<Child>) {
        adapter.children.clear()
        adapter.children.addAll(children)
        adapter.children.add(Child())
        adapter.notifyDataSetChanged()
    }

    override fun onAvatarClicked() {
        findNavController().navigate(ChildrenFragmentDirections.actionChildrenFragmentToAvatarsDialog())
    }

    override fun saveChild(name: String, avatarResId: Int) {
        presenter.saveChild(name, avatarResId)
    }

    override fun onChildSaved(child: Child) {
        adapter.children.removeAt(adapter.children.lastIndex)
        adapter.children.add(child)
        adapter.children.add(Child())
        adapter.notifyDataSetChanged()

        rvChildren.scrollToPosition(adapter.children.size - 2)
    }

    override fun onChildClicked(imageView: ImageView, child: Child) {
        val extras = FragmentNavigatorExtras(imageView to "avatar")
        val bundle = Bundle().apply { putParcelable("child", child) }
        findNavController().navigate(R.id.action_selectChildFragment_to_stampsFragment, bundle, null, extras)
    }

}
