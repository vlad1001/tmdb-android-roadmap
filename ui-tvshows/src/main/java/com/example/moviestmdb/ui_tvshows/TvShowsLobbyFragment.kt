package com.example.moviestmdb.ui_tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviestmdb.ui_tvshows.databinding.FragmentTvshowsLobbyBinding

class TvShowsLobbyFragment : Fragment() {


    lateinit var binding: FragmentTvshowsLobbyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvshowsLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }
}