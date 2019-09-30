package fr.cdreyfus.airqualitysensorapp.core

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header

interface AdafruitApiService {

    @GET("api/v2/user")
    fun getUser(@Header("X-AIO-Key") aioKey: String): Single<ResponseBody>
}