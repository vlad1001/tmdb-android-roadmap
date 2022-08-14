package com.example.moviestmdb.ui_movies.dicover

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
import com.example.moviestmdb.core_ui.util.SpaceItemDecoration
import com.example.moviestmdb.ui_movies.R
import com.example.moviestmdb.ui_movies.databinding.FragmentDiscoverBinding
import com.example.moviestmdb.ui_movies.filter.FiltersBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    lateinit var binding: FragmentDiscoverBinding
    val viewModel: DiscoverViewModel by viewModels()

    lateinit var pagingAdapter: DiscoverMoviesAdapter

    @Inject
    lateinit var tmdbImageManager: TmdbImageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        initAdapter()

        binding.toolbar.run {
            inflateMenu(R.menu.menu_discover)
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.action_filter) {
                    openFilter()
                    true
                } else {
                    false
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.pagingList.collect {
                pagingAdapter.submitData(it)
            }
        }

    }

    private fun openFilter() {
        val dialog = FiltersBottomSheet()
        dialog.show(childFragmentManager, "FiltersBottomSheet")
    }

    private val movieClickListener : (Int) -> Unit = { movieId ->
    }

    private fun initAdapter() {
        pagingAdapter =
            DiscoverMoviesAdapter(
                tmdbImageManager.getLatestImageProvider(),
                movieClickListener
            )

        binding.list.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter

            val spacing = resources.getDimension(com.example.moviestmdb.core_ui.R.dimen.spacing_normal).toInt()
            val decoration = SpaceItemDecoration(
                spacing, -spacing
            )
            addItemDecoration(decoration)
        }
    }
}