package com.example.moviestmdb

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviestmdb.core.data.login.FirebaseAuthStateUserDataSource
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var firebaseAuthStateUserDataSource: FirebaseAuthStateUserDataSource


    private val TOP_LEVEL_DESTINATIONS = setOf(
        com.example.moviestmdb.ui_movies.R.id.navigation_movies_lobby_fragment,
        com.example.moviestmdb.ui_tvshows.R.id.navigation_tvshows_lobby_fragment,
        com.example.moviestmdb.ui_people.R.id.navigation_peoples_lobby_fragment,
        com.example.moviestmdb.ui_settings.R.id.navigation_settings_fragment,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (TOP_LEVEL_DESTINATIONS.contains(destination.id)) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

        lifecycleScope.launchWhenStarted {
            firebaseAuthStateUserDataSource.getBasicUserInfo().collectLatest { result ->
//                val isSignedIn = result?.isSignedIn() ?: false
                val isSignedIn = true
                if (!isSignedIn) {
                    navController.navigate(com.example.moviestmdb.ui_login.R.id.login_graph)
                } else {
                    navController.popBackStack(
                        com.example.moviestmdb.ui_login.R.id.login_graph,
                        false
                    )
                }
            }
        }
    }
}
