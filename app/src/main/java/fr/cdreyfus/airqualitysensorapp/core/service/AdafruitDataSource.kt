package fr.cdreyfus.airqualitysensorapp.core.service

import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.FeedDataPoint
import fr.cdreyfus.airqualitysensorapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdafruitDataSource(private val adafruitApiService: AdafruitApiService) {

    fun getUser(
        aioKey: String,
        handleSuccess: (UserResponse?) -> Unit,
        handleFail: (Throwable) -> Unit
    ) {
        adafruitApiService.getUser(aioKey).enqueue(handleResponse(handleSuccess, handleFail))
    }

    fun getFeedsList(
        username: String,
        aioKey: String,
        handleSuccess: (List<Feed>?) -> Unit,
        handleFail: (Throwable) -> Unit
    ) {
        adafruitApiService.getFeedsList(aioKey, username)
            .enqueue(handleResponse(handleSuccess, handleFail))
    }

    fun getFeedData(
        username: String,
        aioKey: String,
        feedKey: String,
        handleSuccess: (List<FeedDataPoint>?) -> Unit,
        handleFail: (Throwable) -> Unit
    ) {
        adafruitApiService.getFeedDataByKey(aioKey, username, feedKey)
            .enqueue(handleResponse(handleSuccess, handleFail))

    }

}

private fun <T> handleResponse(
    handleSuccess: (T?) -> Unit,
    handleFail: (Throwable) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            handleFail(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            handleSuccess(response.body())
        }
    }
}

