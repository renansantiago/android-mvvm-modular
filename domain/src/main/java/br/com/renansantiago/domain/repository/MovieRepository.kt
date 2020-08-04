package br.com.renansantiago.domain.repository

import br.com.renansantiago.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MovieRepository {

    fun getPopularMovies(page: Int): Single<List<Movie>>

    fun saveFavoriteMovie(movie: Movie): Completable

    fun deleteFavoriteMovie(id: Long): Completable

    fun getFavoritesMovie(): Single<List<Movie>>
}