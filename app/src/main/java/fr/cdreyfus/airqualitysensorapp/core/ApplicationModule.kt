package fr.cdreyfus.airqualitysensorapp.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.cdreyfus.airqualitysensorapp.BuildConfig
import fr.cdreyfus.airqualitysensorapp.ui.login.LoginViewModel
import fr.cdreyfus.airqualitysensorapp.ui.login.RemoteUserDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

val applicationModule = module {

    single {
        Gson()
    }

    single {

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AdafruitApiService::class.java)
    }

    single {
        RemoteUserDataSource(adafruitApiService = get())
    }

    viewModel {
        LoginViewModel(remoteUserDataSource = get())
    }
}