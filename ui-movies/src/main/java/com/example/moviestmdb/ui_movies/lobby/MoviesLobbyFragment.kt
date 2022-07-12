package com.example.moviestmdb.ui_movies.lobby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviestmdb.core.extensions.launchAndRepeatWithViewLifecycle
import com.example.moviestmdb.ui_movies.databinding.FragmentLobbyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class MoviesLobbyFragment: Fragment() {

    lateinit var binding: FragmentLobbyBinding
    private val viewModel: MovieLobbyViewModel by viewModels()

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


        launchAndRepeatWithViewLifecycle {
            viewModel.refresh()
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.state.collect { uiState ->
                Timber.i("### uiStae: ${uiState.refreshing}")

                if(uiState.refreshing) {
                    //loading all
                }



            }

        }

    }
}