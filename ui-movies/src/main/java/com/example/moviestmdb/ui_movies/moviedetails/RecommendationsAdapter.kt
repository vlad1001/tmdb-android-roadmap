package com.example.moviestmdb.ui_movies.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestmdb.Movie
import com.example.moviestmdb.ui_movies.databinding.ListItemMovieRecommendationsBinding
import com.example.moviestmdb.util.TmdbImageUrlProvider

class RecommendationsAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
) : ListAdapter<Movie, RecommendationsViewHolder>(RecommendationsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val binding = ListItemMovieRecommendationsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecommendationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { item ->
            holder.binding.title.text = item.title

            entry.posterPath?.let { posterPath ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getPosterUrl(
                            path = posterPath,
                            imageWidth = holder.itemView.width,
                        )
                    )
                    .into(holder.binding.image)
            }
        }

    }
}

class RecommendationsViewHolder(
    internal val binding: ListItemMovieRecommendationsBinding
) : RecyclerView.ViewHolder(binding.root)

object RecommendationsComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem == newItem
    }
}