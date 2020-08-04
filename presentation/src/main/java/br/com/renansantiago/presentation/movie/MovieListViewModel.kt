package br.com.renansantiago.presentation.movie

import androidx.lifecycle.MutableLiveData
import br.com.renansantiago.domain.exception.DefaultException
import br.com.renansantiago.domain.interactor.FavoriteMoviesUseCase
import br.com.renansantiago.domain.interactor.GetPopularMoviesUseCase
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.presentation.ui.base.BaseViewModel
import br.com.renansantiago.presentation.utils.SingleLiveData

class MovieListViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : BaseViewModel() {

    sealed class State {
        object GoToFavorite : State()
    }

    private var currentPage: Int = 1

    val popularMovies = MutableLiveData<List<Movie>>()

    val error = SingleLiveData<DefaultException>()

    val state = SingleLiveData<State>()

    fun fetchPopularMovies() {
        subscribeSingle(
            observable = getPopularMoviesUseCase.getPopularMovies(currentPage)
                .doOnSubscribe { showLoading.set(true) }
                .doFinally { showLoading.set(false) },
            success = {
                popularMovies.postValue(it)
            },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavoriteMovie(movie: Movie) {
        subscribeCompletable(
            observable = favoriteMoviesUseCase.updateFavoriteMovie(movie),
            complete = { fetchPopularMovies() },
            error = { error.postValue(it) }
        )
    }

    fun onClickFavorite() {
        state.value = State.GoToFavorite
    }

}