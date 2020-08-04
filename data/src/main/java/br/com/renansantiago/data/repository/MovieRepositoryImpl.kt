package br.com.renansantiago.data.repository

import br.com.renansantiago.data.local.db.movie.FavoriteMovieEntity
import br.com.renansantiago.data.local.db.movie.MovieDao
import br.com.renansantiago.data.remote.MovieService
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieRepositoryImpl(
    private val service: MovieService,
    private val dao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return service.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .flattenAsObservable { it }
            .map {
                //For each MovieDto.Response verify if it's a favorite movie in FavoriteMovieEntity by id
                val isFavorite = dao.findFavorite(it.id) != null
                it.toMovie(isFavorite)
            }
            .toList()
    }

    override fun saveFavoriteMovie(movie: Movie): Completable {
        return Completable.create {
            dao.insertFavorite(FavoriteMovieEntity(movie.id))
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun deleteFavoriteMovie(id: Long): Completable {
        return Completable.create {
            dao.deleteFavorite(FavoriteMovieEntity(id))
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    override fun getFavoritesMovie(): Single<List<Movie>> {
        return dao.findFavorites()
            .subscribeOn(Schedulers.io())
            .flattenAsObservable { it }
            .map { entity ->
                try {
                    service.getMovieById(entity.id)
                        .map { it.toMovie(true) }
                        .blockingGet()
                } catch (ex: Exception) {
                    null
                }
            }
            .toList()
            .map { it.filterNotNull() }
    }

}