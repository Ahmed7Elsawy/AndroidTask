package com.example.softxpert.task.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.softxpert.task.R
import com.example.softxpert.task.ui.usecase.GetGenresUseCase
import com.example.softxpert.task.ui.usecase.GetMoviesUseCase
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

        runBlocking {
            val moviesResponse = getMoviesUseCase()
            val genresResponse = getGenresUseCase()

            Log.d("MainActivity", "movies ${moviesResponse.data.toString()}")
            Log.d("MainActivity", "genres ${genresResponse.data.toString()}")
        }

    }
}
