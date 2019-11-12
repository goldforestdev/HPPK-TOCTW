package com.hppk.toctw.ui.children.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hppk.toctw.R
import com.hppk.toctw.common.getAvatarResId
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.DEFAULT_AVATAR
import com.hppk.toctw.data.model.DEFAULT_UNKNOWN_AVATAR
import com.hppk.toctw.data.repository.ChildrenRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_add_child.*


class SharedViewModel : ViewModel() {
    val avatarResName = MutableLiveData<String>()
}

class AddChildFragment : Fragment(), AddChildContract.View {

    private lateinit var model: SharedViewModel

    private val presenter: AddChildContract.Presenter by lazy {
        val childDao = AppDatabase.getInstance(context!!).childrenDao()
        AddChildPresenter(this, ChildrenRepository(childDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        model.avatarResName.observe(this, Observer<String> { avatarResName ->
            if (avatarResName != "ic_unknown_kid") {
                ivUnknownAvatar.setImageResource(context!!.getAvatarResId(avatarResName))
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_child, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        etName.addTextChangedListener {
            btnDone.visibility = when {
                it.toString().isEmpty() -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        ivUnknownAvatar.setOnClickListener { showAvatarSelectDialog() }

        btnDone.setOnClickListener {
            presenter.saveChild(etName.text.toString(), model.avatarResName.value ?: DEFAULT_AVATAR)

            etName.setText("")
            ivUnknownAvatar.setImageResource(R.drawable.ic_unknown_kid)
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setTitle(R.string.add_child)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        model.avatarResName.value = DEFAULT_UNKNOWN_AVATAR
        presenter.unsubscribe()
    }

    private fun showAvatarSelectDialog() {
        findNavController().navigate(AddChildFragmentDirections.actionAddChildFragmentToAvatarsDialog())
    }

    override fun onChildSaved(child: Child) {
        findNavController().navigateUp()
    }

}
