package com.example.softxpert.task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.softxpert.task.R
import com.example.softxpert.task.data.models.Movie
import com.example.softxpert.task.databinding.MovieItemBinding

class MoviesAdapter(
    private val onMovieClicked: (Int) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(UserDiffCallBack()) {

    class MoviesViewHolder(
        private val itemBinding: MovieItemBinding,
        private val onMovieClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(movie: Movie) {
            itemBinding.movieText.text = movie.title
            itemBinding.releaseDate.text = movie.releaseDate
            movie.posterPath.let {
                itemBinding.movieImage.load("https://image.tmdb.org/t/p/w500${movie.backdropPath}") {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
            itemBinding.movieCard.setOnClickListener {
                onMovieClicked(movie.id)
            }
        }
    }

    private class UserDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding, onMovieClicked)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }
}
