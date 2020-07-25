package ppjh.mvvm.koin

import android.app.Application
import org.koin.android.ext.android.startKoin
import ppjh.mvvm.koin.di.helloModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(helloModule))
    }
}