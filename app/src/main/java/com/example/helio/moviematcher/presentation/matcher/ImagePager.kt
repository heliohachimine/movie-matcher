package com.example.helio.moviematcher.presentation.matcher

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.helio.moviematcher.R

class ImagePager : PagerAdapter() {

    private var images: List<String> = ArrayList()

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return images.size
    }

    fun setList(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(view.context).inflate(R.layout.adapter_slider, view, false)

        val imageView = imageLayout.findViewById<ImageView>(R.id.img_slider)
        Glide.with(view).load(images[position]).into(imageView)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }
}
