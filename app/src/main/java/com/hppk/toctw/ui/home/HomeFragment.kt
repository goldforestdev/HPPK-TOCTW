package com.hppk.toctw.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        tv_more_booth.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSchedulesFragment())
        }

        if (auth.currentUser != null && auth.currentUser?.email.equals("admin@hp.com")) {
            tv_admin_add_notice.visibility = View.VISIBLE
        } else {
            tv_admin_add_notice.visibility = View.INVISIBLE
        }

        tv_admin_add_notice.setOnClickListener {
            //TODO:notify notice
        }
    }
}
