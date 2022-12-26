package com.example.moviestmdb.ui_movies.popular

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestmdb.Movie
import com.example.moviestmdb.MovieWithGenere
import com.example.moviestmdb.ui_movies.R
import com.example.moviestmdb.ui_movies.databinding.ListItemPopularMovieBinding
import com.example.moviestmdb.util.TmdbImageUrlProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

class PopularMoviesAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
//    private val tmdbDateFormatter: TmdbDateFormatter,
    private val onItemClickListener: (movieId: Int) -> Unit,
) : PagingDataAdapter<MovieWithGenere, PopularMovieViewHolder>(PopularEntryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val binding = ListItemPopularMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { popularEntry ->
            holder.binding.title.text = popularEntry.movie.title
            holder.binding.subtitle.text = "${popularEntry.movie.voteCount} votes"

//            holder.binding.subtitle.text = "${popularEntry.voteCount} votes • ${
//                tmdbDateFormatter.formatMediumDate(popularEntry.releaseDate)
//            }"

//            holder.binding.popularityBadge.progress = popularEntry.movie.popularityPrecentage

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

class PopularMovieViewHolder(
    internal val binding: ListItemPopularMovieBinding
) : RecyclerView.ViewHolder(binding.root)

object PopularEntryComparator : DiffUtil.ItemCallback<MovieWithGenere>() {
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