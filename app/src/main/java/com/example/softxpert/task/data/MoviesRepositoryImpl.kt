package com.example.softxpert.task.data

import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.MoviesResponse
import com.example.softxpert.task.data.remote.RemoteMoviesDataSource
import com.example.softxpert.task.domain.MoviesRepository

class MoviesRepositoryImpl(private val remoteMoviesDataSource: RemoteMoviesDataSource) :
    MoviesRepository {
    override suspend fun getMovies(page: Int, genresId: Int?): Resource<MoviesResponse> {
        return safeApiCall { remoteMoviesDataSource.getMovies(page, genresId) }
    }

    override suspend fun getGenres(): Resource<GenresResponse> {
        return safeApiCall { remoteMoviesDataSource.getGenres() }
    }
}
