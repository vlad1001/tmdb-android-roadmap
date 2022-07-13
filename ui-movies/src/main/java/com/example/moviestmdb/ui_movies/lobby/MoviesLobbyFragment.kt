package com.example.moviestmdb.ui_movies.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestmdb.core.TmdbImageManager
import com.example.moviestmdb.core.extensions.launchAndRepeatWithViewLifecycle
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.moviestmdb.ui_movies.databinding.FragmentLobbyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesLobbyFragment : Fragment() {

    lateinit var binding: FragmentLobbyBinding
    private val viewModel: MovieLobbyViewModel by viewModels()

    lateinit var popularMoviesAdapter: PopularMoviesCarrouselAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPopularAdapter()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->

                binding.swipeRefresh.isRefreshing = uiState.refreshing

                uiState.message?.let { message ->
                    Snackbar.make(requireView(), message.message, Snackbar.LENGTH_LONG)
                        .setAction("Dismiss") {
                            viewModel.clearMessage(message.id)
                        }
                        .show()
                }


                binding.popularMoviesView.setLoading(uiState.popularRefreshing)
                popularMoviesAdapter.submitList(uiState.popularMovies)
            }
        }
    }

    private val onPopularClick: (Int) -> Unit = { movieId ->
        Timber.i("$movieId")
    }

    private fun initPopularAdapter() {
        popularMoviesAdapter = PopularMoviesCarrouselAdapter(
            tmdbImageManager.getLatestImageProvider(),
            onPopularClick,
        )
        binding.popularMoviesView.recyclerView.run {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val spacing = 20
            addItemDecoration(SpaceItemDecoration(
                spacing, -spacing
            ))
        }

        binding.popularMoviesView.title.text = "Popular Movies"
        binding.popularMoviesView.more.setOnClickListener {
        }
    }
}