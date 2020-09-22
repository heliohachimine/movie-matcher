package com.example.helio.moviematcher.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.data.response.GenreResponse
import com.example.helio.moviematcher.databinding.FragmentProfileBinding
import com.example.helio.moviematcher.presentation.profile.adapter.GenreAdapter
import com.example.helio.moviematcher.presentation.profile.adapter.GenreAdapterListener
import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(), GenreAdapterListener {
    private val viewModel: MovieViewModel by viewModel()
    private val genreAdapter: GenreAdapter by inject()
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        setupRecycler()
        viewModel.getGenresList()
        subscribeLiveData()

        return binding.root
    }

    private fun setupRecycler() {
        genreAdapter.genreAdapterListener = this
        binding.rvGenres.adapter = genreAdapter
        context.let {
            binding.rvGenres.layoutManager = GridLayoutManager(it,3)
        }

    }

    private fun subscribeLiveData() {
        viewModel.genresLiveData.observe(viewLifecycleOwner, Observer { genres ->
            genreAdapter.setList(genres)
        })
    }

    override fun setOnClickGenre(genre: GenreResponse, view: ConstraintLayout) {
        view.background = resources.getDrawable(R.drawable.background_genre_selected)
    }

    companion object {
        fun newInstance() = ProfileFragment()
            .apply {}
    }
}
