package com.example.moviestmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviestmdb.core.data.login.FirebaseAuthStateUserDataSource
import com.example.moviestmdb.core.result.Result
import com.example.moviestmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var firebaseAuthStateUserDataSource: FirebaseAuthStateUserDataSource

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


        lifecycleScope.launchWhenStarted {
            firebaseAuthStateUserDataSource.getBasicUserInfo().collectLatest { result ->
                val isSignedIn = result?.isSignedIn() ?: false
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
