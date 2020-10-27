package co.tamara.example.application

import android.app.Application
import co.tamara.example.data.DataSource


class App: Application() {
    lateinit var dataSource: DataSource

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataSource = DataSource(applicationContext)
    }
    companion object{
        lateinit var instance: App
            private set
    }
}