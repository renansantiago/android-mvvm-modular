package br.com.renansantiago.presentation.movie.favorite

import androidx.lifecycle.MutableLiveData
import br.com.renansantiago.domain.exception.DefaultException
import br.com.renansantiago.domain.interactor.FavoriteMoviesUseCase
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.presentation.ui.base.BaseViewModel
import br.com.renansantiago.presentation.utils.SingleLiveData

class FavoriteMovieListViewModel(
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : BaseViewModel() {

    val favoriteMovies = MutableLiveData<List<Movie>>()

    val error = SingleLiveData<DefaultException>()

    fun fetchFavoriteMovies() {
        subscribeSingle(
            observable = favoriteMoviesUseCase.getFavoriteMovies()
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = { favoriteMovies.postValue(it) },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavoriteMovie(movie: Movie) {
        subscribeCompletable(
            observable = favoriteMoviesUseCase.updateFavoriteMovie(movie),
            complete = { fetchFavoriteMovies() },
            error = { error.postValue(it) }
        )
    }

}