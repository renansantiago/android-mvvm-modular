package br.com.renansantiago.mvvmmodular.di

import br.com.renansantiago.data.local.db.AppDatabase
import br.com.renansantiago.data.remote.MovieService
import br.com.renansantiago.data.remote.interceptor.RemoteRequestInterceptor
import br.com.renansantiago.data.remote.interceptor.RxRemoteErrorInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val dataModule = module {

    factory { RxRemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor(getProperty("api_key")) }

    single {
        AppDatabase.createDatabase(
            androidApplication(),
            getProperty("database_name")
        )
    }

    single { get<AppDatabase>().movieDao() }

    single {
        MovieService.createMovieService(
            getProperty("base_url"),
            get(),
            get()
        )
    }
}