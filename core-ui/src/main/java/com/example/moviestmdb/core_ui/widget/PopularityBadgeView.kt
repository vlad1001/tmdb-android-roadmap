package com.example.moviestmdb.core_ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.moviestmdb.core_ui.R
import com.example.moviestmdb.core_ui.databinding.PopularityBadgeViewBinding
import com.example.moviestmdb.core_ui.util.CircularOutlineProvider
import com.google.android.material.card.MaterialCardView

class PopularityBadgeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private var binding: PopularityBadgeViewBinding

    init {

        binding = PopularityBadgeViewBinding.inflate(LayoutInflater.from(context), this, true)
        outlineProvider = CircularOutlineProvider
    }

    var progress: Int = 0
        set(value) {
            field = value
            binding.progressBar.progress = value
            updateProgressColor()
            updateProgressLable()
        }

    private fun updateProgressLable() {
        binding.textView.text = "${progress}%"
    }

    private fun updateProgressColor() {
        when (progress) {
            in 0..29 -> {
                val color = ContextCompat.getColor(context, R.color.tmdb_popularity_low)
                binding.progressBar.setIndicatorColor(color)
            }
            in 30..69 -> {
                val color = ContextCompat.getColor(context, R.color.tmdb_popularity_mid)
                binding.progressBar.setIndicatorColor(color)
            }
            in 70..100 -> {
                val color = ContextCompat.getColor(context, R.color.tmdb_popularity_high)
                binding.progressBar.setIndicatorColor(color)
            }
        }
    }
}