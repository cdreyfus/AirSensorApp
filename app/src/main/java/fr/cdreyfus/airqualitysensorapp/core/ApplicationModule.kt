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

val applicationModule = module {

    single {
        Gson()
    }
    single {
        val logInterceptor = HttpLoggingInterceptor { msg -> Timber.d(msg) }
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder().addInterceptor(logInterceptor).build()

    }
    single {

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
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