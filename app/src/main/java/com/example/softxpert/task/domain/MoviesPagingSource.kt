package com.example.softxpert.task.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.domain.usecase.GetMoviesUseCase
import com.example.softxpert.task.domain.usecase.SearchMoviesUseCase

class MoviesPagingSource(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val genreId: Int?,
    private val query: String,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: 1
        val result = if (query.isEmpty()) getMoviesUseCase(page, genreId)
        else searchMoviesUseCase(page, query)

        return LoadResult.Page(
            data = result.data?.movies ?: emptyList(),
            prevKey = when (page) {
                1 -> null
                else -> page - 1
            },
            nextKey = page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
