package fr.cdreyfus.airqualitysensorapp.core.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.cdreyfus.airqualitysensorapp.BuildConfig
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitApiService
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val applicationModule = module {

    single {
        Gson()
    }

    single {

        val logInterceptor = HttpLoggingInterceptor { msg -> Timber.d(msg) }
        logInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()))
            .build()
            .create(AdafruitApiService::class.java)
    }

    single {
        AdafruitDataSource(adafruitApiService = get())
    }
}