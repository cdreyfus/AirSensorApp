package fr.cdreyfus.airqualitysensorapp.core.service

import fr.cdreyfus.airqualitysensorapp.ui.login.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AdafruitApiService {

    @GET("api/v2/user")
    fun getUser(@Header("X-AIO-Key") aioKey: String): Call<UserResponse>
}