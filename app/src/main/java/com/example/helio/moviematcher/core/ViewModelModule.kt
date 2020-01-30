package com.example.helio.moviematcher.core

import com.example.helio.moviematcher.presentation.viewmodel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        MovieViewModel(
            get()
        )
    }
}