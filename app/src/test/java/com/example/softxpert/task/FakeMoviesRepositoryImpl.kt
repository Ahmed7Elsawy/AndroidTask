package com.example.softxpert.task

import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.Genre
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.data.models.MoviesResponse
import com.example.softxpert.task.domain.MoviesRepository

class FakeMoviesRepositoryImpl : MoviesRepository {

    val genres = mutableListOf<Genre>()

    fun addGenres(list: List<Genre>) {
        genres.addAll(list)
    }

    override suspend fun getMovies(page: Int, genresId: Int?): Resource<MoviesResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getGenres(): Resource<GenresResponse> {
        return Resource.Success(GenresResponse(genres))
    }

    override suspend fun searchMovie(page: Int, query: String): Resource<MoviesResponse> {
        TODO("Not yet implemented")
    }
}
