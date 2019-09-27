package fr.cdreyfus.airqualitysensorapp

import android.app.Application
import fr.cdreyfus.airqualitysensorapp.core.applicationModule
import org.koin.android.ext.android.startKoin

class AirQualitySensorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this@AirQualitySensorApplication
            , modules = listOf(applicationModule)
        )

    }
}