package com.hppk.toctw.ui.booth.details.reviews


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Review
import com.hppk.toctw.ui.booth.details.ReviewsAdapter
import kotlinx.android.synthetic.main.fragment_reviews.*


class ReviewsFragment : Fragment(), ReviewsContract.View {

    private val args: ReviewsFragmentArgs by navArgs()
    private val reviewsAdapter: ReviewsAdapter by lazy { ReviewsAdapter(context!!) }
    private val presenter: ReviewsContract.Presenter by lazy {
        ReviewsPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_reviews, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        rvReviews.layoutManager = LinearLayoutManager(context)
        rvReviews.adapter = reviewsAdapter
        presenter.getAllReviews(args.booth)
    }

    override fun onReviewsLoaded(reviews: List<Review>) {
        reviewsAdapter.reviews.clear()
        reviewsAdapter.reviews.addAll(reviews)
        reviewsAdapter.notifyDataSetChanged()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setTitle(R.string.review)
            }
        }
    }

    override fun showProgressBar(visibility: Int) {
        progressBar.visibility = visibility
    }

}
