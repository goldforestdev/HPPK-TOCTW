package com.hppk.toctw.ui.children.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender
import com.hppk.toctw.data.repository.ChildrenRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_add_children.*


class AddChildrenFragment : Fragment(), AddChildrenContract.View {

    private var gender: Gender = Gender.BOY
    private val presenter: AddChildrenContract.Presenter by lazy {
        AddChildrenPresenter(
            this,
            ChildrenRepository(AppDatabase.getInstance(context!!).childrenDao())
        )
    }
    private val adapter: ChildrenAdapter by lazy { ChildrenAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_children, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvChildren.layoutManager = LinearLayoutManager(context)
        rvChildren.adapter = adapter

        ivGender.setOnClickListener {
            val genderIconRes = when (gender) {
                Gender.BOY -> {
                    gender = Gender.GIRL
                    R.drawable.ic_female
                }
                else -> {
                    gender = Gender.BOY
                    R.drawable.ic_male
                }
            }
            ivGender.setImageResource(genderIconRes)
        }
        etName.addTextChangedListener {
            ivAdd.visibility = when {
                it.toString().isNullOrEmpty() -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        ivAdd.setOnClickListener {
            presenter.saveChild(etName.text.toString(), gender)
        }
        btnDone.setOnClickListener {
            fragmentManager?.popBackStackImmediate()
        }
    }

    override fun onDestroyView() {
        presenter.unsubscribe()
        super.onDestroyView()
    }

    override fun onChildAdded(child: Child) {
        val position = adapter.children.size
        adapter.children.add(child)
        adapter.notifyItemInserted(position)

        btnDone.visibility = View.VISIBLE
    }

    override fun clearEditText() {
        etName.setText("")
    }

}
