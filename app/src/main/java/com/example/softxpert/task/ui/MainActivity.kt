package com.example.softxpert.task.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.softxpert.task.R
import com.example.softxpert.task.data.remote.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            val moviesResponse = apiService.getMovies()
            val genresResponse = apiService.getGenres()

            Log.d("MainActivity", "movies ${moviesResponse.body().toString()}")
            Log.d("MainActivity", "genres ${genresResponse.body().toString()}")
        }

    }
}
