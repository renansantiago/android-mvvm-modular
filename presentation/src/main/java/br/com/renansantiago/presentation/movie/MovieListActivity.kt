package br.com.renansantiago.presentation.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.presentation.R
import br.com.renansantiago.presentation.databinding.ActivityMoviesBinding
import br.com.renansantiago.presentation.movie.favorite.FavoriteMovieListActivity
import br.com.renansantiago.presentation.ui.base.BaseActivity
import br.com.renansantiago.presentation.ui.extensions.observeNotNull
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : BaseActivity<ActivityMoviesBinding>(), MovieRecyclerAdapter.OnMovieClickListener {

    val viewModel by viewModel<MovieListViewModel>()

    private val movieRecyclerAdapter by inject<MovieRecyclerAdapter>()

    override fun getLayoutRes(): Int = R.layout.activity_movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initRecyclerView()

        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPopularMovies()
    }

    private fun initRecyclerView() {
        movieRecyclerAdapter.listener = this
        binding.rvMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvMovies.adapter = movieRecyclerAdapter
    }

    private fun observeData() {
        viewModel.popularMovies.observeNotNull(this) {
            movieRecyclerAdapter.notifyChanged(it)
        }

        viewModel.error.observeNotNull(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

        viewModel.state.observeNotNull(this) {
            when (it) {
                MovieListViewModel.State.GoToFavorite -> startActivity(
                    Intent(
                        this,
                        FavoriteMovieListActivity::class.java
                    )
                )
            }
        }
    }

    override fun onClickFavoriteMovie(movie: Movie) {
        viewModel.onClickFavoriteMovie(movie)
    }
}