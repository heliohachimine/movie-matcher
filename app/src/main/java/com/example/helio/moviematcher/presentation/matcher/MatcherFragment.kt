package com.example.helio.moviematcher.presentation.matcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.data.response.MovieResponse
import com.example.helio.moviematcher.databinding.FragmentMatcherBinding
import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import org.koin.android.viewmodel.ext.android.viewModel

class MatcherFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    lateinit var binding: FragmentMatcherBinding
    var listMovies = ArrayList<MovieResponse>()
    var page = (1..100).random()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matcher, container, false)

        binding.acceptBtn.setOnClickListener {
            binding.swipeView.doSwipe(true)
        }
        binding.rejectBtn.setOnClickListener {
            binding.swipeView.doSwipe(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMoviesByGenre()
    }

    private fun getMoviesByGenre() {
        viewModel.getPopularMovies(page)
        viewModel.moviesByGenreLiveData.observe(
            viewLifecycleOwner,
            Observer { movies ->
                movies.random()
                movies.forEach { movie ->
                    listMovies.add(movie)
                }
                binding.swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                    .setDisplayViewCount(2)
                    .setSwipeDecor(
                        SwipeDecor()
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view)
                    )
                listMovies.forEach { movie ->
                    context?.let {
                        binding.swipeView.addView(
                            MovieCard(it, movie, binding.swipeView)
                        )
                    }
                }
                binding.swipeView.addItemRemoveListener {
                    if (it < 2) {
                        listMovies.clear()
                        page = (1..100).random()
                        // binding.tvHotValue.text = page.toString()
                        viewModel.getPopularMovies(page)
                    }
                }
            }
        )
    }

    companion object {
        fun newInstance() = MatcherFragment()
            .apply {}
    }
}
