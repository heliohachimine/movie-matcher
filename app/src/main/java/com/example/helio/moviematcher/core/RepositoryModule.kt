package com.example.helio.moviematcher.core

import com.example.helio.moviematcher.data.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        MovieRepository(
            service = get()
        )
    }
}