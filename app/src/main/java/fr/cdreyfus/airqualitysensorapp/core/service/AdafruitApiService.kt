package fr.cdreyfus.airqualitysensorapp.core.service

import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.FeedDataPoint
import fr.cdreyfus.airqualitysensorapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AdafruitApiService {

    @GET("api/v2/user")
    fun getUser(@Header("X-AIO-Key") aioKey: String): Call<UserResponse>

    @GET("/api/v2/{username}/feeds")
    fun getFeedsList(@Header("X-AIO-Key") aioKey: String, @Path("username") username: String): Call<List<Feed>>


    @GET("/api/v2/{username}/feeds/{feed_key}/data")
    fun getFeedDataByKey(
        @Header("X-AIO-Key") aioKey: String,
        @Path("username") username: String, @Path("feed_key") feedKey: String, @Query("limit") limit: Int = 10
    ): Call<List<FeedDataPoint>>

}
