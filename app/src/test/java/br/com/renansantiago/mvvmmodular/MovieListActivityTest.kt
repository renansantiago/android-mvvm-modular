package br.com.renansantiago.mvvmmodular

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.presentation.movie.MovieListActivity
import br.com.renansantiago.presentation.movie.MovieRecyclerAdapter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

/**
 * Created by Renan Santiago on 04/02/19.
 */
@RunWith(RobolectricTestRunner::class)
class MovieListActivityTest : KoinTest {

    companion object {
        private const val POSITION = 0
    }

    private val observerPopularMoviesMock: Observer<List<Movie>> = mock()

    private lateinit var controller: ActivityController<MovieListActivity>

    @Before
    fun `Setup test`() {
        controller = Robolectric.buildActivity(MovieListActivity::class.java).create()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun `Finish test`() {
        StandAloneContext.stopKoin()
        RxJavaPlugins.reset()
        controller.destroy()
    }

    @Test
    fun `Test fetchPopularMovies() when activity is resumed`() {
        //Prepare
        val activity = controller.get()
        activity.viewModel.popularMovies.observeForever(observerPopularMoviesMock)

        //Action
        controller.resume()

        //Test
        verify(observerPopularMoviesMock).onChanged(any())
    }

    @Test
    fun `Test onClickFavoriteMovie() when success occur and favorite attribute change correctly`() {
        //Prepare
        val activity = controller.visible().get()
        val recyclerView = activity.findViewById<RecyclerView>(R.id.rv_movies)
        val adapter = recyclerView.adapter as MovieRecyclerAdapter
        activity.viewModel.popularMovies.observeForever(observerPopularMoviesMock)

        controller.resume() //First need to load Movies into Adapter

        //Action
        val movieClicked = adapter.getMovieByPosition(POSITION)
        recyclerView.findViewHolderForAdapterPosition(POSITION)
            ?.itemView
            ?.findViewById<AppCompatImageView>(R.id.iv_star)
            ?.performClick()

        //Test
        verify(
            observerPopularMoviesMock,
            times(2)
        ).onChanged(any())

        val movieUpdated = activity.viewModel.popularMovies.value?.get(POSITION)
        assertEquals(
            !movieClicked.favorite,
            movieUpdated?.favorite
        )
    }
}