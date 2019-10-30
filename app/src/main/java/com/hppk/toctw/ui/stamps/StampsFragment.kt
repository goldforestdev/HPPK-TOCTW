package com.hppk.toctw.ui.stamps


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Stamp
import kotlinx.android.synthetic.main.fragment_stamps.*

private const val REQUEST_CODE_PERMISSIONS = 10

class StampsFragment : Fragment(), StampsContract.View {

    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private val presenter: StampsContract.Presenter by lazy {
        StampsPresenter(this)
    }
    private val adapter: StampsAdapter by lazy { StampsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_stamps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter.getStamps()
    }

    private fun initView() {
        rvStamps.layoutManager = GridLayoutManager(context, 3)
        rvStamps.adapter = adapter

        fabStamp.isEnabled = true
        fabStamp.setOnClickListener { showCameraView() }
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun onStampsLoaded(stamps: List<Stamp>) {
        adapter.stamps.clear()
        adapter.stamps.addAll(stamps)
        adapter.notifyDataSetChanged()
    }

    private fun showCameraView() {
        fabStamp.isEnabled = false

        if (hasCameraPermission()) {
            findNavController().navigate(StampsFragmentDirections.actionStampsFragmentToQRCameraFragment())
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                findNavController().navigate(StampsFragmentDirections.actionStampsFragmentToQRCameraFragment())
            }
        }
    }

    private fun hasCameraPermission() = REQUIRED_PERMISSIONS.all { perm ->
        ContextCompat.checkSelfPermission(context!!, perm) == PackageManager.PERMISSION_GRANTED
    }
}
