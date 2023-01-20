package com.example.softxpert.task.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.GenresResponse
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.domain.MoviesPagingSource
import com.example.softxpert.task.domain.usecase.GetGenresUseCase
import com.example.softxpert.task.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val genreId = MutableStateFlow<Int?>(null)

    private val _genresResponse: MutableLiveData<Resource<GenresResponse>> =
        MutableLiveData(Resource.Loading())
    val genresResponse: LiveData<Resource<GenresResponse>> = _genresResponse


    val items: Flow<PagingData<Movie>> = genreId.flatMapLatest { genreId ->
        Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(getMoviesUseCase, genreId) }
        ).flow.cachedIn(viewModelScope)
    }

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            val response = getGenresUseCase()
            _genresResponse.value = response
        }
    }

    fun selectGenre(newGenreId: Int?) {
        viewModelScope.launch {
            genreId.value = newGenreId
            val genres =
                genresResponse.value?.data?.genres?.map { genre -> genre.copy(isSelected = genre.id == newGenreId) }

            genres?.let {
                genresResponse.value?.apply { this.data?.genres = genres }?.let {
                    _genresResponse.value = it
                }
            }
        }
    }

}
