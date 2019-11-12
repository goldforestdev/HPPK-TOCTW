package com.hppk.toctw.ui.children.avatars


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.ui.children.add.SharedViewModel
import kotlinx.android.synthetic.main.dialog_avatars.*


class AvatarsDialog : DialogFragment(), AvatarsAdapter.AvatarClickListener {

    private val adapter: AvatarsAdapter by lazy {
        AvatarsAdapter(
            context!!,
            listOf(
                "ic_boy",
                "ic_boy2",
                "ic_girl",
                "ic_girl2",
                "ic_boy3",
                "ic_boy4",
                "ic_girl3",
                "ic_girl4",
                "ic_boy5",
                "ic_boy6",
                "ic_girl5",
                "ic_girl6",
                "ic_boy7",
                "ic_boy8",
                "ic_girl7",
                "ic_girl8"
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

    override fun onAvatarClicked(resName: String) {
        model.avatarResName.value = resName
        dismiss()
    }

}
