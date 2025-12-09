package br.com.vandodev.copa_2022_android

import android.app.Application
import br.com.vandodev.copa_2022_android.di.AppContainer

class Copa2026AndroidApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}