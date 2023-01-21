package com.example.softxpert.task.domain.usecase

import com.example.softxpert.task.FakeMoviesRepositoryImpl
import com.example.softxpert.task.data.models.Genre
import com.example.softxpert.task.domain.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.google.common.truth.Truth.assertThat


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class GetGenresUseCaseTest {


    private lateinit var getGenresUseCase: GetGenresUseCase
    private lateinit var fakeBurgersRepository: MoviesRepository

    private val genres = listOf(
        Genre(1, "Action", null),
        Genre(2, "Adventure", null),
        Genre(3, "Animation", null),
        Genre(4, "Comedy", null),
        Genre(5, "Crime", null),
    )

    @Before
    fun setup() {
        fakeBurgersRepository = FakeMoviesRepositoryImpl()
        getGenresUseCase = GetGenresUseCase(fakeBurgersRepository)
    }

    @Test
    fun `test if All genre is Added Return All in the first index`() = runTest {
        // Given
        (fakeBurgersRepository as FakeMoviesRepositoryImpl).addGenres(genres)

        // When
        val result = getGenresUseCase().data?.genres

        // Then
        assertThat(result?.size).isEqualTo(genres.size + 1)
        assertThat(result?.get(0)?.name).isEqualTo("All")
    }

    @Test
    fun `test if All genre is Selected Return All selected = true`() = runTest {
        // Given
        (fakeBurgersRepository as FakeMoviesRepositoryImpl).addGenres(genres)

        // When
        val result = getGenresUseCase().data?.genres

        // Then
        assertThat(result?.get(0)?.isSelected).isEqualTo(true)
    }

    @Test
    fun `test if other genres are not Selected Return selected = false`() = runTest {
        // Given
        (fakeBurgersRepository as FakeMoviesRepositoryImpl).addGenres(genres)

        // When
        val result = getGenresUseCase().data?.genres

        // Then
        assertThat(result?.get(1)?.isSelected).isEqualTo(false)
        assertThat(result?.get(2)?.isSelected).isEqualTo(false)
        assertThat(result?.get(3)?.isSelected).isEqualTo(false)
        assertThat(result?.get(4)?.isSelected).isEqualTo(false)
        assertThat(result?.get(5)?.isSelected).isEqualTo(false)
    }
}
