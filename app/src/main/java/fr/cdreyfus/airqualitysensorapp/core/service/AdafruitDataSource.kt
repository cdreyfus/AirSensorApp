package fr.cdreyfus.airqualitysensorapp.core.service

import androidx.lifecycle.MutableLiveData
import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.FeedDataPoint
import fr.cdreyfus.airqualitysensorapp.model.User
import fr.cdreyfus.airqualitysensorapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AdafruitDataSource(private val adafruitApiService: AdafruitApiService) {

    fun getUser(user: MutableLiveData<User>, aioKey: String) {
        adafruitApiService.getUser(aioKey).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Timber.e(t)
                user.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val newUser = response.body()?.user?.copy(aioKey = aioKey)
                user.postValue(newUser)
            }
        })
    }

    fun getFeedsList(feedList: MutableLiveData<List<Feed>>, username: String, aioKey: String) {

        adafruitApiService.getFeedsList(aioKey, username).enqueue(object : Callback<List<Feed>> {
            override fun onFailure(call: Call<List<Feed>>, t: Throwable) {
                Timber.e(t)
                feedList.postValue(emptyList())
            }

            override fun onResponse(call: Call<List<Feed>>, response: Response<List<Feed>>) {
                feedList.postValue(response.body())
            }

        })
    }

    fun getFeedData(username: String, aioKey: String, feedKey: String) {
        adafruitApiService.getFeedDataByKey(aioKey, username, feedKey)
            .enqueue(object : Callback<List<FeedDataPoint>> {
                override fun onFailure(call: Call<List<FeedDataPoint>>, t: Throwable) {
                    Timber.e(t)
                }

                override fun onResponse(
                    call: Call<List<FeedDataPoint>>,
                    response: Response<List<FeedDataPoint>>
                ) {
                    println(response.body())
                }

            })

    }
}