package com.example.softxpert.task.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softxpert.task.R
import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.databinding.FragmentMoviesListBinding
import com.example.softxpert.task.ui.adapters.GenresAdapter
import com.example.softxpert.task.ui.adapters.MoviesAdapter
import com.example.softxpert.task.ui.utils.MarginItemDecoration
import com.example.softxpert.task.ui.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val moviesListViewModel: MoviesListViewModel by viewModels()
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        initializeSearchBar()
        initializeMoviesList()
        initializeGenresList()

        return binding.root
    }

    private fun initializeGenresList() {
        genresAdapter = GenresAdapter { moviesListViewModel.selectGenre(it) }
        binding.genresList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }
        moviesListViewModel.genresResponse.observe(viewLifecycleOwner) { genresResponse ->
            if (genresResponse is Resource.Success) {
                genresResponse.data?.genres?.let {
                    genresAdapter.submitList(it)
                }
            } else if (genresResponse is Resource.Error) {
                Toast.makeText(requireContext(), genresResponse.message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initializeMoviesList() {
        moviesAdapter = MoviesAdapter { navigateToMovieDetails(it) }
        binding.moviesList.apply {
            adapter = moviesAdapter
            setHasFixedSize(true)
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            addItemDecoration(
                MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.small_margin))
            )
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                moviesListViewModel.moviesPagingData.collectLatest {
                    moviesAdapter.submitData(it)
                }
            }
        }
    }

    private fun navigateToMovieDetails(movie: Movie) {
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
            Json.encodeToString(movie)
        )
        findNavController().navigate(action)
    }

    private fun initializeSearchBar() {
        binding.searchButton.setOnClickListener {
            binding.searchEt.clearFocus()
            hideKeyboard()
            handleSearch(binding.searchEt.text.toString())
        }
        binding.searchEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                handleSearch(v.text.toString())
                hideKeyboard()
            }
            false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun handleSearch(query: String) {
        moviesListViewModel.searchMovie(query)
    }

}
