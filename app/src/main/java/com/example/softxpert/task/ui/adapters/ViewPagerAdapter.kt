package com.example.softxpert.task.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.softxpert.task.databinding.ImageItemBinding
import com.example.softxpert.task.ui.moviedetails.showImage
import java.util.*

class ViewPagerAdapter(val context: Context, private val imageList: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemBinding = ImageItemBinding.inflate(mLayoutInflater, container, false)
        itemBinding.movieImage.showImage(imageList[position])
        Objects.requireNonNull(container).addView(itemBinding.root)
        return itemBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
