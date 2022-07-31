package com.example.moviestmdb.ui_movies.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestmdb.core.TmdbImageManager
import com.example.moviestmdb.core.extensions.launchAndRepeatWithViewLifecycle
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.moviestmdb.ui_movies.R
import com.example.moviestmdb.ui_movies.databinding.FragmentMovieDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    lateinit var castsAdapter: CastAdapter
    lateinit var recommendationsAdapter: RecommendationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        initCastsAdapter()
        initRecommendationsAdapter()

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->
                Timber.i("### $uiState")

                bindUi(uiState)
            }
        }

        binding.overviewTitle.setOnClickListener {
            firebaseAuth.signOut()
        }
    }

    private fun bindUi(uiState: MovieDetailsViewState) {
        binding.overview.text = uiState.movie?.overView

        with(uiState) {
            movie?.let {
                binding.voteAvrage.text = "${(movie.voteAverage * 10).toInt()}%"
                binding.voteCount.text = "${movie.voteCount / 1000}K votes"

//                binding.status.text = movie.status
//                binding.badget.text = movie.budget.toString()
//                binding.revenue.text = movie.revenue.toString()

                movie.backdropPath?.let {
                    Glide.with(requireView())
                        .load(
                            tmdbImageManager.getLatestImageProvider()
                                .getBackdropUrl(it, binding.backdrop.width)
                        )
                        .into(binding.backdrop)
                }

                castsAdapter.submitList(casts)
                recommendationsAdapter.submitList(recommendetions)

                movie.posterPath?.let {
                    Glide.with(requireView())
                        .load(
                            tmdbImageManager.getLatestImageProvider()
                                .getPosterUrl(it, binding.posterImage.width)
                        )
                        .into(binding.posterImage)
                }

                val isFavoriteMovie = uiState.favouriteMovies.contains(movie.id.toString())
                val iconDrawable =
                    if (isFavoriteMovie) com.example.moviestmdb.core_ui.R.drawable.ic_favorite
                    else com.example.moviestmdb.core_ui.R.drawable.ic_favorite_border
                binding.favoritesFab.setIconResource(iconDrawable)// = ContextCompat.getDrawable(requireContext(), iconDrawable)

                binding.favoritesFab.setOnClickListener {
                    if (isFavoriteMovie) {
                        viewModel.removeFromFavorites()
                    } else {
                        viewModel.addToFavorites()
                    }
                }
            }


        }

    }

    private fun initCastsAdapter() {
        castsAdapter =
            CastAdapter(
                tmdbImageManager.getLatestImageProvider(),
            )

        binding.castList.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = castsAdapter
            val spacing = 20
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)

        }
    }

    private fun initRecommendationsAdapter() {
        recommendationsAdapter =
            RecommendationsAdapter(
                tmdbImageManager.getLatestImageProvider(),
            )

        binding.recommendationsList.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = recommendationsAdapter
            val spacing = 20
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)

        }
    }
}