package com.example.movies_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies_application.R
import com.example.movies_application.network.models.MoviesItem

class WatchlistAdapter(
    private val listener: (MoviesItem) -> Unit
): ListAdapter<MoviesItem, WatchlistAdapter.MovieViewHolder>(MovieWatchlistDiffCallBack()) {

    class MovieViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val movieImage: ImageView = itemView.findViewById(R.id.imV_movieIcon)
        private val title: TextView = itemView.findViewById(R.id.movie_label)
        private val rating: TextView = itemView.findViewById(R.id.tv_rating)

        fun set(movie: MoviesItem, holder: MovieViewHolder) {
            val titleText = movie.title
            title.text = titleText

            Glide.with(holder.itemView).load(movie.posterurl).into(movieImage)

            val ratingText = "${movie.calcAverageRatingValue()}/10"
            rating.text = ratingText
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.watchlist_movie_item_layout, parent, false)
                return MovieViewHolder(view)
            }
        }
    }

    public override fun getItem(position: Int): MoviesItem {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.set(movie, holder)
        holder.itemView.setOnClickListener { listener(movie) }
    }
}

class MovieWatchlistDiffCallBack: DiffUtil.ItemCallback<MoviesItem>() {
    override fun areItemsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
        return oldItem == newItem
    }
}