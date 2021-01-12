package com.ewerton.hotmartapplication.screens.locations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewerton.hotmartapplication.R
import com.ewerton.hotmartapplication.models.AllLocationsResult

class LocationsRecyclerViewAdapter(
    private val context: Context,
    list: ArrayList<AllLocationsResult>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<LocationsRecyclerViewAdapter.LocationsFragViewHolder>() {

    var mItemList = list

    interface OnItemClickListener {
        fun onItemClicked(model: AllLocationsResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsFragViewHolder {
        return LocationsFragViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
        )
    }

    fun updateListItems(updatedList: ArrayList<AllLocationsResult>) {
        mItemList.clear()
        mItemList = updatedList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    override fun onBindViewHolder(holder: LocationsFragViewHolder, position: Int) {
        holder.bind(mItemList[position], itemClickListener)
    }

    private fun setImage(view: ImageView, model: AllLocationsResult) {
        val height = if (model.id % 2 == 0) "200" else "300"
        Glide.with(context).load("https://picsum.photos/id/${model.id}/200/${height}")
            .fallback(android.R.drawable.stat_notify_error)
            .timeout(4500)
            .into(view)
    }

    inner class LocationsFragViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val image: ImageView = item.findViewById(R.id.imageView)
        private val name: TextView = item.findViewById(R.id.textName)
        private val review: RatingBar = item.findViewById(R.id.ratingBar)
        private val type: TextView = item.findViewById(R.id.textType)
        private val reviewNumber: TextView = item.findViewById(R.id.textRating)

        fun bind(model: AllLocationsResult, clickListener: OnItemClickListener) {
            setImage(image, model)
            name.text = model.name
            review.rating = model.review.toFloat()
            type.text = model.type
            reviewNumber.text = model.review.toString()

            itemView.setOnClickListener {
                clickListener.onItemClicked(model)
            }
        }
    }
}