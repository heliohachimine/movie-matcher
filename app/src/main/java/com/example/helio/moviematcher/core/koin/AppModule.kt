package com.example.helio.moviematcher.core.koin

import com.example.helio.moviematcher.BuildConfig
import com.example.helio.moviematcher.data.service.MoviesService
import com.example.helio.moviematcher.presentation.matcher.ImagePager
import com.example.helio.moviematcher.presentation.profile.adapter.GenreAdapter
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    single (named("INTERCEPTOR")) {
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url:HttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("INTERCEPTOR")))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(MoviesService::class.java)
    }

    factory {
        GenreAdapter()
    }

    factory { ImagePager() }

}
