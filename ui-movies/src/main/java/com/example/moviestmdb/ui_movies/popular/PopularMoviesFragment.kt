package com.example.moviestmdb.ui_movies.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviestmdb.core.TmdbImageManager
import com.example.moviestmdb.core.extensions.launchAndRepeatWithViewLifecycle
import com.example.moviestmdb.core_ui.R.dimen
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.moviestmdb.ui_movies.R
import com.example.moviestmdb.ui_movies.databinding.FragmentPopularMoviesBinding
import com.example.moviestmdb.ui_movies.popular.vo.FilterChip
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesFragment: Fragment() {

    lateinit var binding: FragmentPopularMoviesBinding
    private val viewModel: PopularMoviesViewModel by viewModels()

    lateinit var pagingAdapter: PopularMoviesAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMoviesBinding.inflate(inflater)

        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        binding.toolbar.apply {
            title = "Popular Movies"
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.pagedList.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.filterChips.collect { chips ->
                val list = mutableListOf<Chip>()
                chips.forEach { chip ->
                    val c = createChip(chip)
                    c.setOnCheckedChangeListener { compoundButton, isChecked ->
                        viewModel.toggleFilter(compoundButton.id, isChecked)
                    }
                    list.add(c)
                }

                binding.chipGroup.removeAllViews()
                list.forEach {
                    binding.chipGroup.addView(it)
                }
            }
        }
    }

    private val movieClickListener : (Int) -> Unit = { movieId ->
    }

    private fun initAdapter() {
        pagingAdapter =
            PopularMoviesAdapter(
                tmdbImageManager.getLatestImageProvider(),
//                tmdbDateFormatter,
                movieClickListener
            )

        binding.list.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter

            val spacing = resources.getDimension(dimen.spacing_normal).toInt()
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)
        }
    }


    private fun createChip(filterChip: FilterChip): Chip {
        val chip = LayoutInflater.from(binding.chipGroup.context).inflate(R.layout.item_filter_chip, null, false) as Chip
        chip.id = filterChip.id
        chip.text = filterChip.text
        return chip
    }
}