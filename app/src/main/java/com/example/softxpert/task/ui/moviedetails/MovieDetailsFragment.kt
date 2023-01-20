package com.example.softxpert.task.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.softxpert.task.R
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.databinding.FragmentMovieDetailsBinding
import com.example.softxpert.task.ui.adapters.ViewPagerAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val movie = Json.decodeFromString<Movie>(args.movieModel)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as AppCompatActivity?)?.supportActionBar?.title = movie.title

        viewMovieData(movie)
        initializeViewPager(movie)

        return binding.root
    }

    private fun viewMovieData(movie: Movie) {
        binding.movieText.text = movie.title
        binding.description.text = movie.overview
        binding.releaseDate.text = movie.releaseDate
        binding.rate.text = movie.voteAverage.toString()
    }

    private fun initializeViewPager(movie: Movie) {
        val viewPagerAdapter = ViewPagerAdapter(
            requireContext(),
            listOf(movie.backdropPath, movie.posterPath)
        )
        binding.viewPagerMain.adapter = viewPagerAdapter
    }

}

fun ImageView.showImage(imagePath: String?) {
    imagePath.let {
        this.load("https://image.tmdb.org/t/p/w500${imagePath}") {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}
