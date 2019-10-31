package com.hppk.toctw.ui.children.avatars


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.ui.children.SharedViewModel
import kotlinx.android.synthetic.main.dialog_avatars.*


class AvatarsDialog : DialogFragment(), AvatarsAdapter.AvatarClickListener {

    private val adapter: AvatarsAdapter by lazy {
        AvatarsAdapter(
            listOf(
                R.drawable.ic_boy,
                R.drawable.ic_boy2,
                R.drawable.ic_girl,
                R.drawable.ic_girl2,
                R.drawable.ic_boy3,
                R.drawable.ic_boy4,
                R.drawable.ic_girl3,
                R.drawable.ic_girl4,
                R.drawable.ic_boy5,
                R.drawable.ic_boy6,
                R.drawable.ic_girl5,
                R.drawable.ic_girl6,
                R.drawable.ic_boy7,
                R.drawable.ic_boy8,
                R.drawable.ic_girl7,
                R.drawable.ic_girl8
            ),
            this
        )
    }

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_avatars, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAvatars.layoutManager = GridLayoutManager(context, 4)
        rvAvatars.adapter = adapter
    }

    override fun onAvatarClicked(resId: Int) {
        model.avatarResId.value = resId
        dismiss()
    }

}
