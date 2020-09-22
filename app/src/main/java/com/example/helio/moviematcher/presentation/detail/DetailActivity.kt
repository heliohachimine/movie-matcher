package com.example.helio.moviematcher.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.databinding.ActivityDetailBinding
import com.example.helio.moviematcher.presentation.matcher.ImagePager
import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.URLEncoder
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()
    private val imagePager: ImagePager by inject()
    lateinit var binding: ActivityDetailBinding
    var movieId by Delegates.notNull<Long>()
    private val imgs = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        supportActionBar?.hide()
        binding.ibComeBack.setOnClickListener {
            onBackPressed()
        }
        movieId = intent.getLongExtra("movie_id", 0)
        getMovieData(movieId)
        getImages(movieId)

    }

    private fun getImages(id: Long) {
        viewModel.getImagesMovie(id)
        viewModel.movieImagesLiveData.observe(this, Observer { images ->
            images.backdrops?.forEach {
                if (it.aspect_ratio != null && it.aspect_ratio == 1.0)
                imgs.add("https://image.tmdb.org/t/p/original/" + it.file_path)
            }
            imagePager.setList(imgs)
            binding.movieBackdrop.adapter = imagePager
            binding.dotsIndicator.setViewPager(binding.movieBackdrop)
        })
    }

    private fun getMovieData(id: Long) {
        viewModel.getSingleMovie(id)
        viewModel.singleMovieLiveData.observe(this, Observer { movie ->
            binding.movieOverview.text = movie.overview
            binding.movieTitle.text = movie.title
            binding.spotifyBtn.setOnClickListener {
                openSpotify(movie.title)
            }
            binding.netflixBtn.setOnClickListener {
                openNetflix(movie.title)
            }
        })
        viewModel.movieImagesLiveData.observe(this, Observer { images ->
            images.backdrops?.forEach {
                if (imgs.size >= 10) return@forEach
                imgs.add("https://image.tmdb.org/t/p/original" + it.file_path)
            }
        })
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
