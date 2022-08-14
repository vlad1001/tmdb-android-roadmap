package com.example.moviestmdb.ui_movies.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moviestmdb.Genere
import com.example.moviestmdb.core.extensions.launchAndRepeatWithViewLifecycle
import com.example.moviestmdb.ui_movies.databinding.BottomSheetFiltersBinding
import com.example.moviestmdb.ui_movies.databinding.ItemFilterChipBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FiltersBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetFiltersBinding
    val viewModel: FiltersBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.generes.collect { generes ->
                bindGeneres(generes)
            }
        }
    }

    private fun bindGeneres(generes: List<Genere>) {
        val views = generes.map { genere ->
            val chip = ItemFilterChipBinding.inflate(layoutInflater).root as Chip
            chip.id = genere.id
            chip.text = genere.name
            chip
        }

        binding.chipGroup.removeAllViews()
        views.forEach {
            binding.chipGroup.addView(it)
        }

    }
}