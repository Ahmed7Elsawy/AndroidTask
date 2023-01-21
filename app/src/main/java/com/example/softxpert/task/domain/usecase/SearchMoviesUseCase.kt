package com.example.softxpert.task.domain.usecase

import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.MoviesResponse
import com.example.softxpert.task.domain.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(page: Int, query: String): Resource<MoviesResponse> {
        return moviesRepository.searchMovie(page, query)
    }
}
