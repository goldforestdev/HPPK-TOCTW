package com.hppk.toctw.ui.booth

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.repository.FavoritesRepository
import com.hppk.toctw.data.source.local.AppDatabase
import kotlinx.android.synthetic.main.fragment_booth.*
import kotlinx.android.synthetic.main.item_booth_list.*

class BoothFragment : Fragment(), BoothContract.View, BoothAdapter.BoothClickLister,
    BoothAdapter.BusyClickLister, BoothStaffDialog.BoothBusyStatusClickListener,
    BoothAdapter.StampClickLister, SwipeRefreshLayout.OnRefreshListener, BoothAdapter.FavoritesClickLister {

    private val presenter: BoothContract.Presenter by lazy {
        val db = AppDatabase.getInstance(context!!)
        BoothPresenter(this, favoritesRepository = FavoritesRepository(localFavoritesDao = db.favoritesDao()))
    }
    private val boothAdapter: BoothAdapter by lazy {
        BoothAdapter(
            boothClickLister = this,
            busyClickLister = this,
            stampClickLister = this,
            favoritesClickLister = this
        )
    }

    private var viewType = VIEW_TYPE_PHOTO
    private var favoritesMode = false
    private var favoritesIdList: MutableList<String> = mutableListOf()
    private var boothList : MutableList<Booth> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initData()
        initFloating()
    }

    private fun initFloating() {
        flFavorites.setOnClickListener {
            favoritesMode = !favoritesMode
            if (favoritesMode) {
                flFavorites.setImageResource(R.drawable.ic_menu)
            } else {
                flFavorites.setImageResource(R.drawable.ic_star_white)
            }
            showBoothList(boothList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_booth_list, menu)

        val icon = when (viewType) {
            VIEW_TYPE_PHOTO -> R.drawable.ic_format_list
            else -> R.drawable.ic_photo
        }
        val menuItem = menu.findItem(R.id.menuViewType)
        menuItem.setIcon(icon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuViewType) {
            val (type, icon) = when (viewType) {
                VIEW_TYPE_PHOTO -> VIEW_TYPE_LIST to R.drawable.ic_photo
                else -> VIEW_TYPE_PHOTO to R.drawable.ic_format_list
            }

            viewType = type
            boothAdapter.viewType = type
            boothAdapter.notifyDataSetChanged()
            item.setIcon(icon)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
                actionBar.setTitle(R.string.program)
            }
        }
    }

    private fun initData() {
        presenter.loadCollection()
    }

    private fun initRecyclerView() {
        srlBoothMain.setOnRefreshListener(this)
        srlBoothMain.setColorSchemeResources(R.color.colorPrimaryDark)
        rcBoothList.layoutManager = LinearLayoutManager(activity)
        rcBoothList.adapter = boothAdapter
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onError(errTitle: Int, errMsg: Int) {

    }

    private fun showBoothList(boothDataList: List<Booth>) {
        boothAdapter.booths.clear()
        if (favoritesMode) {
           for (boothData in boothDataList) {
               for (favoritesId in favoritesIdList) {
                   if (boothData.id == favoritesId) {
                       boothAdapter.booths.add(boothData)
                   }
               }
           }
        } else {
            boothAdapter.booths.addAll(boothDataList)
        }

        boothAdapter.notifyDataSetChanged()
    }

    override fun onBoothListLoaded(boothDataList: List<Booth>, favorites: List<Favorites>) {
        favoritesIdList.clear()
        boothList.clear()
        for (favoritesData in favorites) {
            favoritesIdList.add(favoritesData.id)
        }
        boothList = boothDataList.toMutableList()
        boothAdapter.favorites.clear()
        boothAdapter.favorites.addAll(favoritesIdList)
        showBoothList(boothList)

    }

    override fun onBoothClick(booth: Booth) {
        findNavController().navigate(BoothFragmentDirections.actionBoothFragmentToBoothDetailsFragment(booth))
    }

    override fun onStampClick(booth: Booth) {
        findNavController().navigate(BoothFragmentDirections.actionBoothFragmentToChildrenFragment())
    }

    override fun busyState(booth: Booth) {
        presenter.updateBoothInfo(booth)
    }


    override fun onUpdateBoothInfoSuccess() {
        boothAdapter.notifyDataSetChanged()
    }

    override fun onBusyClick(booth: Booth) {

        if (AppAuth.isStaff) {
            val boothStaffDialog = BoothStaffDialog(this)
            val bundle = Bundle()
            bundle.putParcelable(BOOTH_INFO, booth)
            boothStaffDialog.arguments = bundle
            boothStaffDialog.show(activity!!.supportFragmentManager, null)
        }

    }

    override fun onFavoritesClick(booth: Booth) {
        if (favoritesIdList.contains(booth.id)) {
            ivStar.setImageResource(R.drawable.ic_star_border)
            presenter.deleteFavoritesData(booth)
            favoritesIdList.remove(booth.id)
        } else {
            ivStar.setImageResource(R.drawable.ic_star_selected)
            presenter.saveFavoritesData(booth)
            favoritesIdList.add(booth.id)
        }

        boothAdapter.favorites.clear()
        boothAdapter.favorites.addAll(favoritesIdList)
        showBoothList(boothList)
        boothAdapter.notifyDataSetChanged()
    }

    override fun showWaitingView(show: Boolean) {
        if (show) {
            rcBoothList.visibility = View.GONE
            srlBoothMain.isRefreshing = true
        } else {
            rcBoothList.visibility = View.VISIBLE
            srlBoothMain.isRefreshing = false
        }
    }

    override fun onRefresh() {
        presenter.loadCollection()
    }
}
