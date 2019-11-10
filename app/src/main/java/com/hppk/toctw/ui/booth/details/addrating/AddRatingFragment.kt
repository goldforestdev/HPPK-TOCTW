package com.hppk.toctw.ui.booth.details.addrating


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hppk.toctw.R
import com.hppk.toctw.data.model.User
import kotlinx.android.synthetic.main.fragment_add_rating.*


class AddRatingFragment : Fragment(), AddRatingContract.View {

    private val args: AddRatingFragmentArgs by navArgs()
    private val presenter: AddRatingContract.Presenter by lazy {
        AddRatingPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_rating, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        presenter.getMe()
        ratingBar.rating = args.rating
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add_review, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.title = args.booth.title
            }
        }
    }

    override fun onMeLoaded(me: User) {
        tvName.text = me.name
        Glide.with(this).load(me.profile).circleCrop().into(ivProfile)
    }

}
