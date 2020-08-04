package br.com.renansantiago.mvvmmodular.di

import com.google.gson.Gson
import org.koin.dsl.module.module

/**
 * Created by Renan Santiago on 11/02/19.
 */

val appModule = module {

    single { Gson() }

}