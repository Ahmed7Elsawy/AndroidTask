package com.example.softxpert.task.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.softxpert.task.R
import com.example.softxpert.task.domain.usecase.GetGenresUseCase
import com.example.softxpert.task.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getMoviesUseCase: GetMoviesUseCase

    @Inject
    lateinit var getGenresUseCase: GetGenresUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)


        runBlocking {
            val moviesResponse = getMoviesUseCase()
            val genresResponse = getGenresUseCase()

            Log.d("MainActivity", "movies ${moviesResponse.data.toString()}")
            Log.d("MainActivity", "genres ${genresResponse.data.toString()}")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
