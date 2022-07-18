package com.example.moviestmdb.ui_people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviestmdb.ui_people.databinding.FragmentPeopelLobbyBinding

class PeopleLobbyFragment : Fragment() {

    lateinit var binding: FragmentPeopelLobbyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPeopelLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }
}