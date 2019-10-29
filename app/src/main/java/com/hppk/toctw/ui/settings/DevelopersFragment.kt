package com.hppk.toctw.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.fragment_developers.*


class DevelopersFragment : Fragment() {
    private var clickLYJ = false
    private var clickKHJ = false
    private var clickKYH = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvYjLim.setOnClickListener {
            clickLYJ = true
            checkAuthEnable()
        }

        cvHjKim.setOnClickListener {
            clickKHJ = true
            checkAuthEnable()
        }

        cvYhKim.setOnClickListener {
            clickKYH = true
            checkAuthEnable()
        }
    }

    private fun checkAuthEnable() {
        if (clickLYJ && clickKHJ && clickKYH) {
            showAuth()
        }
    }

    private fun showAuth(): Boolean {
        findNavController().navigate(DevelopersFragmentDirections.actionDevelopersFragmentToAuthFragment())
        return true
    }
}
