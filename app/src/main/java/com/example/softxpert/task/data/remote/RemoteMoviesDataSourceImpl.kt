package com.example.softxpert.task.data.remote

import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import retrofit2.Response

class RemoteMoviesDataSourceImpl(private val apiService: ApiService) : RemoteMoviesDataSource {
    override suspend fun getMovies(page: Int, genresId: Int?): Response<MoviesResponse> {
        return apiService.getMovies(page, genresId)
    }

    override suspend fun getGenres(): Response<GenresResponse> {
        return apiService.getGenres()
    }

    override suspend fun searchMovie(page: Int, query: String): Response<MoviesResponse> {
        return apiService.searchMovie(page, query)
    }
}
