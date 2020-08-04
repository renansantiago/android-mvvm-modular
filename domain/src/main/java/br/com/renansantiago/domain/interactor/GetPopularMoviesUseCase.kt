package br.com.renansantiago.domain.interactor

import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.domain.repository.MovieRepository
import io.reactivex.Single

/**
 * Created by Renan Santiago on 31/01/19.
 */
class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {

    fun getPopularMovies(page: Int): Single<List<Movie>> {
        return movieRepository.getPopularMovies(page)
    }
}