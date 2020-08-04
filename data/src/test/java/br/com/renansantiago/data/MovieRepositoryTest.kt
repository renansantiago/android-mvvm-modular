package br.com.renansantiago.data

import br.com.renansantiago.data.local.db.movie.FavoriteMovieEntity
import br.com.renansantiago.data.local.db.movie.MovieDao
import br.com.renansantiago.data.remote.MovieService
import br.com.renansantiago.data.remote.ResponseWrap
import br.com.renansantiago.data.remote.dto.MovieDto
import br.com.renansantiago.data.repository.MovieRepositoryImpl
import br.com.renansantiago.domain.model.Movie
import br.com.renansantiago.domain.repository.MovieRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

/**
 * Created by Renan Santiago on 04/02/19.
 */
class MovieRepositoryTest {

    private val serviceMock = mock<MovieService>()

    private val daoMock = mock<MovieDao>()

    private lateinit var repository: MovieRepository

    @Before
    fun `Setup test`() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        repository = MovieRepositoryImpl(
            serviceMock,
            daoMock
        )
    }

    @Test
    fun `Test getPopularMovies() when movie id is NOT found in favorite`() {
        //Prepare
        `when`(serviceMock.getPopularMovies(any()))
            .thenReturn(Single.just(ResponseWrap(listOf(getMovieDtoResponseMock()))))

        `when`(daoMock.findFavorite(1))
            .thenReturn(null)

        //Action
        val testObserver = repository.getPopularMovies(any()).test()

        //Test
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(getMovieMock(false)))
    }

    @Test
    fun `Test getPopularMovies() when movie id is found in favorite`() {
        //Prepare
        `when`(serviceMock.getPopularMovies(any()))
            .thenReturn(Single.just(ResponseWrap(listOf(getMovieDtoResponseMock()))))

        `when`(daoMock.findFavorite(1))
            .thenReturn(FavoriteMovieEntity(1))

        //Action
        val testObserver = repository.getPopularMovies(any()).test()

        //Test
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(getMovieMock(true)))
    }

    private fun getMovieMock(favorite: Boolean): Movie {
        return Movie(
            1,
            "title",
            5,
            "/image.jpg",
            favorite
        )
    }

    private fun getMovieDtoResponseMock(): MovieDto.Response {
        return MovieDto.Response(
            1,
            "title",
            5.0,
            "/image.jpg"
        )
    }

    private fun getMovieEntityMock(id: Long = 1): FavoriteMovieEntity {
        return FavoriteMovieEntity(id)
    }

}