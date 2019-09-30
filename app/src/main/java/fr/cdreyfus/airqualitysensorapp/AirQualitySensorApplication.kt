package fr.cdreyfus.airqualitysensorapp

import android.app.Application
import fr.cdreyfus.airqualitysensorapp.core.modules.applicationModule
import fr.cdreyfus.airqualitysensorapp.core.modules.viewModelsModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class AirQualitySensorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin(
            this@AirQualitySensorApplication
            , modules = listOf(applicationModule, viewModelsModule)
        )

    }
}