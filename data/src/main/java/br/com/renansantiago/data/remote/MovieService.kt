package br.com.renansantiago.data.remote

import br.com.renansantiago.data.remote.dto.MovieDto
import br.com.renansantiago.data.remote.interceptor.RemoteRequestInterceptor
import br.com.renansantiago.data.remote.interceptor.RxRemoteErrorInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<ResponseWrap<MovieDto.Response>>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id: Long): Single<MovieDto.Response>

    companion object {
        fun createMovieService(
            baseUrl: String,
            requestInterceptor: RemoteRequestInterceptor,
            rxRemoteErrorInterceptor: RxRemoteErrorInterceptor
        ): MovieService {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(rxRemoteErrorInterceptor)
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build()
                .create(MovieService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }
}