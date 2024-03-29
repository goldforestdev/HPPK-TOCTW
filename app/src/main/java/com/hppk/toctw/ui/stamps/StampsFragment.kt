package com.hppk.toctw.ui.stamps


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.hppk.toctw.R
import com.hppk.toctw.common.getAvatarResId
import com.hppk.toctw.data.model.DEFAULT_UNKNOWN_AVATAR
import com.hppk.toctw.data.repository.StampRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_stamps.*


private const val REQUEST_CODE_PERMISSIONS = 10

class StampsFragment : Fragment(), StampsContract.View {

    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    private val args: StampsFragmentArgs by navArgs()
    private val presenter: StampsContract.Presenter by lazy {

        val db = AppDatabase.getInstance(context!!)
        StampsPresenter(this, StampRepository(db.stampDao(), db.childStampDao()))
    }
    private val adapter: StampsAdapter by lazy { StampsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_stamps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()

        if (args.child.avatar != DEFAULT_UNKNOWN_AVATAR) {
            ivAvatar.setImageResource(context!!.getAvatarResId(args.child.avatar))
        }

        presenter.getStamps(args.child)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        collapsingToolbarLayout.title = getString(R.string.child_stamp, args.child.name)
    }

    private fun initView() {
        rvStamps.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when (VIEW_TYPE_LABEL) {
                    adapter.getItemViewType(position) -> 2
                    else -> 1
                }

            }
        }
        rvStamps.adapter = adapter

        fabMissionClear.setOnClickListener { showCameraView() }
    }

    override fun onDestroy() {
        presenter.unsubscribe()
        super.onDestroy()
    }

    override fun onStampsLoaded(stamps: List<Any>) {
        adapter.stamps.clear()
        adapter.stamps.addAll(stamps)
        adapter.notifyDataSetChanged()
    }

    override fun showQRButton(show: Boolean) {
        if (show) {
            fabMissionClear.show()
        } else {
            fabMissionClear.hide()
        }
    }

    private fun showCameraView() {
        if (hasCameraPermission()) {
            findNavController().navigate(
                StampsFragmentDirections.actionStampsFragmentToQRCameraFragment(
                    args.child
                )
            )
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                findNavController().navigate(
                    StampsFragmentDirections.actionStampsFragmentToQRCameraFragment(
                        args.child
                    )
                )
            }
        }
    }

    private fun hasCameraPermission() = REQUIRED_PERMISSIONS.all { perm ->
        ContextCompat.checkSelfPermission(context!!, perm) == PackageManager.PERMISSION_GRANTED
    }

}
