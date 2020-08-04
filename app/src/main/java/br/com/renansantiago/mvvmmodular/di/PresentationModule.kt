package br.com.renansantiago.mvvmmodular.di

import br.com.renansantiago.presentation.movie.MovieListViewModel
import br.com.renansantiago.presentation.movie.MovieRecyclerAdapter
import br.com.renansantiago.presentation.movie.favorite.FavoriteMovieListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    viewModel { MovieListViewModel(get(), get()) }

    viewModel { FavoriteMovieListViewModel(get()) }

    factory { MovieRecyclerAdapter() }

}