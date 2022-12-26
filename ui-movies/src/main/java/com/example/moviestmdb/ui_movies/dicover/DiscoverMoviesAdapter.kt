package com.example.moviestmdb.ui_movies.dicover

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestmdb.MovieWithGenere
import com.example.moviestmdb.ui_movies.R
import com.example.moviestmdb.ui_movies.databinding.ListItemDiscoverMovieBinding
import com.example.moviestmdb.util.TmdbImageUrlProvider
import com.google.android.material.chip.Chip

class DiscoverMoviesAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
//    private val tmdbDateFormatter: TmdbDateFormatter,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<MovieWithGenere, DiscoverMovieViewHolder>(DiscoverEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder {
        val binding = ListItemDiscoverMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DiscoverMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { movieEntry ->
            holder.binding.title.text = movieEntry.movie.title
            holder.binding.subtitle.text = "${movieEntry.movie.voteCount} votes"

//            holder.binding.popularityBadge.progress = movieEntry.movie.popularityPrecentage

            entry.movie.posterPath?.let { posterPath ->
                Glide.with(holder.itemView)
                    .load(
                        tmdbImageUrlProvider.getPosterUrl(
                            path = posterPath,
                            imageWidth = holder.itemView.width,
                        )
                    )
                    .into(holder.binding.image)
            }

            holder.binding.root.setOnClickListener {
                onItemClickListener(entry.movie.id)
            }

            holder.binding.chipGroup.removeAllViews()
            entry.movie.genreList.forEach { genereId ->
                val genereName = entry.generes.firstOrNull { it.id == genereId }?.name
                genereName?.let {
                    val chip = createChip(holder.itemView.context, genereName)
                    holder.binding.chipGroup.addView(chip)
                }
            }
        }
    }

    private fun createChip(context: Context, title: String): Chip {
        val chip = LayoutInflater.from(context).inflate(R.layout.item_chip, null, false) as Chip
        chip.text = title
        return chip
    }
}

class DiscoverMovieViewHolder(
    internal val binding: ListItemDiscoverMovieBinding
) : RecyclerView.ViewHolder(binding.root)

object DiscoverEntryComparator : DiffUtil.ItemCallback<MovieWithGenere>() {
    override fun areItemsTheSame(
        oldItem: MovieWithGenere,
        newItem: MovieWithGenere
    ): Boolean {
        // Id is unique.
        return oldItem.movie.id == newItem.movie.id
    }

    override fun areContentsTheSame(
        oldItem: MovieWithGenere,
        newItem: MovieWithGenere
    ): Boolean {
        return oldItem == newItem
    }
}