package com.example.helio.moviematcher.presentation.activity

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
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
import java.net.URI
import java.net.URLEncoder
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()
    private val sliderAdapter: SliderAdapter by inject()
    lateinit var binding: ActivityDetailBinding
    var movieId by Delegates.notNull<Long>()
    val imgs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.hide()
        binding.ibComeBack.setOnClickListener {
            onBackPressed()
        }
        movieId = intent.getLongExtra("movie_id", 0)
        getMovieData(movieId)
//        getKeywordsData(movieId)
        getImages(movieId)
//        setupRecycler()

    }

    private fun getKeywordsData(id: Long) {
        viewModel.getMovieKeywords(id)
        viewModel.movieKeywordsLiveData.observe(this, Observer { keywords ->
            keywords.forEach { keyword ->
                val textView = TextView(this)
                textView.text = keyword.name
                textView.setTextColor(resources.getColor(R.color.white))
                textView.background = resources.getDrawable(R.drawable.background_keyword)

            }

        })
    }
    private fun getImages(id: Long) {
        viewModel.getImagesMovie(id)
        viewModel.movieImagesLiveData.observe(this, Observer { images ->
            images.backdrops?.forEach {
                if (it.aspect_ratio != null && it.aspect_ratio == 1.0)
                imgs.add("https://image.tmdb.org/t/p/original/" + it.file_path)
            }
            sliderAdapter.setList(imgs)
            setupRecycler()
        })
    }

    private fun getMovieData(id: Long) {
        viewModel.getSingleMovie(id)
        viewModel.singleMovieLiveData.observe(this, Observer { movie ->
            binding.movieOverview.text = movie.overview
            binding.movieTitle.text = movie.title
//            imgs.add("https://image.tmdb.org/t/p/original" + movie.posterPath)
//            imgs.add("https://image.tmdb.org/t/p/original" + movie.backdropPath)
            binding.spotifyBtn.setOnClickListener {
                openSpotify(movie.title)
            }
            binding.netflixBtn.setOnClickListener {
                openNetflix(movie.title)
            }
        })
        viewModel.movieImagesLiveData.observe(this, Observer { images ->
            images.backdrops?.forEach {
                imgs.add("https://image.tmdb.org/t/p/original" + it.file_path)
            }
//            sliderAdapter.setList(imgs)
        })
    }

    private fun setupRecycler(){
        binding.movieBackdrop.adapter = sliderAdapter
        binding.movieBackdrop.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun openSpotify(title: String) {
        val launcher = Intent(Intent.ACTION_VIEW)
        launcher.data = Uri.parse("https://open.spotify.com/search/" + URLEncoder.encode(title, "UTF-8"))
        startActivity(launcher)
    }

    private fun openNetflix(title: String) {
        val launcher = Intent(Intent.ACTION_VIEW)
        launcher.data = Uri.parse("https://www.netflix.com/search/" + title)
        startActivity(launcher)
    }
}
