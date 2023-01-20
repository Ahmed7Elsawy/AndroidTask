package com.example.softxpert.task.ui.movieslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.softxpert.task.data.Resource
import com.example.softxpert.task.databinding.FragmentMoviesListBinding
import com.example.softxpert.task.ui.adapters.GenresAdapter
import com.example.softxpert.task.ui.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val moviesListViewModel: MoviesListViewModel by viewModels()
    private lateinit var genresAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        binding.searchButton.setOnClickListener {
            binding.searchEt.clearFocus()
            hideKeyboard()
            handleSearch(binding.searchEt.text.toString())
        }

        binding.searchEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                handleSearch(v.text.toString())
            }
            false
        }

        genresAdapter = GenresAdapter { moviesListViewModel.selectGenre(it) }
        binding.genresList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.genresList.adapter = genresAdapter


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

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun handleSearch(query: String) {
        Log.d("MoviesListFragment", query)
    }

}
