package com.example.softxpert.task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.softxpert.task.R
import com.example.softxpert.task.data.models.Genre
import com.example.softxpert.task.databinding.GenresItemBinding

class GenresAdapter(
    private val onGenreClicked: (Int?) -> Unit
) : ListAdapter<Genre, GenresAdapter.GenresViewHolder>(UserDiffCallBack()) {

    class GenresViewHolder(
        private val itemBinding: GenresItemBinding,
        private val onGenreClicked: (Int?) -> Unit
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(genre: Genre) {
            itemBinding.genresName.text = genre.name
            if (genre.isSelected == true)
                itemBinding.genresName.setBackgroundResource(R.color.purple_200)
            else
                itemBinding.genresName.setBackgroundResource(R.color.gray)

            itemBinding.genresName.setOnClickListener {
                onGenreClicked(genre.id)
            }
        }
    }

    private class UserDiffCallBack : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val itemBinding =
            GenresItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(itemBinding, onGenreClicked)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
