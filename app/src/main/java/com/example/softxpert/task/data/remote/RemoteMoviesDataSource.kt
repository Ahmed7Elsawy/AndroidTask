package com.example.softxpert.task.data.remote

import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import retrofit2.Response

interface RemoteMoviesDataSource {
    suspend fun getMovies(page: Int, genresId: Int?): Response<MoviesResponse>
    suspend fun getGenres(): Response<GenresResponse>
}
