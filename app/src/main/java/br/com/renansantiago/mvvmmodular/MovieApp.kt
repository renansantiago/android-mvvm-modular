package br.com.renansantiago.mvvmmodular

import android.app.Application
import br.com.renansantiago.mvvmmodular.di.appModule
import br.com.renansantiago.mvvmmodular.di.dataModule
import br.com.renansantiago.mvvmmodular.di.domainModule
import br.com.renansantiago.mvvmmodular.di.presentationModule
import org.koin.android.ext.android.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            androidContext = this,
            modules = listOf(
                appModule,
                dataModule,
                domainModule,
                presentationModule
            ),
            loadPropertiesFromFile = true
        )
    }
}