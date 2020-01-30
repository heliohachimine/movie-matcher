package com.example.helio.moviematcher.presentation.activity

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.databinding.ActivityDetailBinding
import com.example.helio.moviematcher.databinding.ActivityMatcherBinding
import com.example.helio.moviematcher.presentation.adapter.GenreAdapter
import com.example.helio.moviematcher.presentation.adapter.SliderAdapter
import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()
    private val sliderAdapter: SliderAdapter by inject()
    lateinit var binding: ActivityDetailBinding
    var movieId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.hide()
        binding.ibComeBack.setOnClickListener {
            onBackPressed()
        }
        movieId = intent.getLongExtra("movie_id", 0)
        getMovieData(movieId)
        getKeywordsData(movieId)
        setupRecycler()

    }

    private fun getKeywordsData(id: Long) {
        viewModel.getMovieKeywords(id)
        viewModel.movieKeywordsLiveData.observe(this, Observer { keywords ->
            keywords.forEach { keyword ->
                val textView = TextView(this)
                textView.text = keyword.name
                textView.setTextColor(resources.getColor(R.color.white))
                textView.background = resources.getDrawable(R.drawable.background_keyword)
                binding.keywordsLayout.addView(textView)

            }

        })
    }

    private fun getMovieData(id: Long) {
        viewModel.getSingleMovie(id)
        viewModel.singleMovieLiveData.observe(this, Observer { movie ->
            binding.movieOverview.text = movie.overview
            binding.movieTitle.text = movie.title
            val imgs = ArrayList<String>()
            imgs.add("https://image.tmdb.org/t/p/original" + movie.posterPath)
            imgs.add("https://image.tmdb.org/t/p/original" + movie.backdropPath)
            binding.spotifyBtn.setOnClickListener {
                openSpotify(movie.title)
            }

            sliderAdapter.setList(imgs)
        })
    }

    private fun setupRecycler(){
        binding.movieBackdrop.adapter = sliderAdapter
        binding.movieBackdrop.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    fun openSpotify(title: String) {
        val launcher = Intent(Intent.ACTION_VIEW)
        launcher.action = MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH
        launcher.component = ComponentName(
            "com.spotify.music", "com.spotify.music.MainActivity"
        )
        launcher.putExtra(SearchManager.QUERY, title)
        try {
            startActivity(launcher)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "É necessário ter o Spotify instalado", Toast.LENGTH_SHORT).show()
        }

    }
}
