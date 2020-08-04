package br.com.renansantiago.mvvmmodular.di

import br.com.renansantiago.data.repository.MovieRepositoryImpl
import br.com.renansantiago.domain.interactor.FavoriteMoviesUseCase
import br.com.renansantiago.domain.interactor.GetPopularMoviesUseCase
import br.com.renansantiago.domain.repository.MovieRepository
import org.koin.dsl.module.module

val domainModule = module {

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    factory { GetPopularMoviesUseCase(get()) }

    factory { FavoriteMoviesUseCase(get()) }
}