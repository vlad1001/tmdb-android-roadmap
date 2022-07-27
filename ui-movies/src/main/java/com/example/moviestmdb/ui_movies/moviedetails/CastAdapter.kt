package com.example.moviestmdb.ui_movies.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestmdb.Cast
import com.example.moviestmdb.core_ui.databinding.ListItemCastCardBinding
import com.example.moviestmdb.util.TmdbImageUrlProvider

class CastAdapter(
    private val tmdbImageUrlProvider: TmdbImageUrlProvider,
) : ListAdapter<Cast, CastsViewHolder>(CastsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastsViewHolder {
        val binding = ListItemCastCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CastsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        val entry = getItem(position)
        entry?.let { item ->
            holder.binding.title.text = item.name
            holder.binding.subtitle.text = item.character

            entry.profilePath?.let { posterPath ->
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

class CastsViewHolder(
    internal val binding: ListItemCastCardBinding
) : RecyclerView.ViewHolder(binding.root)

object CastsComparator : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(
        oldItem: Cast,
        newItem: Cast
    ): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Cast,
        newItem: Cast
    ): Boolean {
        return oldItem == newItem
    }
}