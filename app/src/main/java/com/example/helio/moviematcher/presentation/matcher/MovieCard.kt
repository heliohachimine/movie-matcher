package com.example.helio.moviematcher.presentation.matcher

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.data.response.MovieResponse
import com.example.helio.moviematcher.presentation.detail.DetailActivity
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeInState
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState

@Layout(R.layout.card_movie)
class MovieCard(context: Context, movie: MovieResponse, swipeView: SwipePlaceHolderView) {
    @View(R.id.posterImageView)
    lateinit var posterImageView: ImageView
    @View(R.id.title_movie)
    lateinit var titleTxt: TextView
    @View(R.id.tv_rating)
    lateinit var tvRating: TextView
    private val mMovie: MovieResponse = movie
    private val mContext: Context = context
    private val mSwipeView: SwipePlaceHolderView = swipeView
    @Resolve
    private fun onResolved() {
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w342" + mMovie.posterPath)
            .into(posterImageView)
        titleTxt.text = mMovie.title
        tvRating.text = mMovie.rating.toString()
        posterImageView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("movie_id", mMovie.id)
            mContext.startActivity(intent)
        }
    }

    @SwipeOut
    private fun onSwipedOut() {
        Log.d("EVENT", "onSwipedOut")
    }

    @SwipeCancelState
    private fun onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState")
    }

    @SwipeIn
    private fun onSwipeIn() {
        Log.d("EVENT", "onSwipedIn")
    }

    @SwipeInState
    private fun onSwipeInState() {
        Log.d("EVENT", "onSwipeInState")
    }

    @SwipeOutState
    private fun onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState")
    }
}
