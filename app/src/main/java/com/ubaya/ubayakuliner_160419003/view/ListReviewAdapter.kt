package com.ubaya.ubayakuliner_160419003.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.ubayakuliner_160419003.R
import com.ubaya.ubayakuliner_160419003.model.Review
import com.ubaya.ubayakuliner_160419003.util.loadImage
import kotlinx.android.synthetic.main.review_list_item.view.*

class ListReviewAdapter(val listReview:ArrayList<Review>) : RecyclerView.Adapter<ListReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.review_list_item, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = listReview[position]
        with(holder.view) {
            textReviewUsername.text = review.user.username
            ratingBarDetailReview.rating = review.rating
            textReview.text = review.message
            textReviewDate.text = review.date
            imageReviewUser.loadImage(
                review.user.photoURL,
                progressLoadingReviewUserPhoto
            )
        }
    }

    override fun getItemCount() = listReview.size

    fun updateListReview(newListReview: ArrayList<Review>) {
        listReview.clear()
        listReview.addAll(newListReview)
        notifyDataSetChanged()
    }
}