package com.example.softxpert.task.data.remote

import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import retrofit2.Response

class RemoteMoviesDataSourceImpl(private val apiService: ApiService) : RemoteMoviesDataSource {
    override suspend fun getMovies(genresId: String?): Response<MoviesResponse> {
        return apiService.getMovies(genresId)
    }

    override suspend fun getGenres(): Response<GenresResponse> {
        return apiService.getGenres()
    }
}
