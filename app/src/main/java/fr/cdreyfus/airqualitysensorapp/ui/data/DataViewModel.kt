package fr.cdreyfus.airqualitysensorapp.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitDataSource
import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.FeedDataPoint
import fr.cdreyfus.airqualitysensorapp.model.User

class DataViewModel(val adafruitDataSource: AdafruitDataSource) : ViewModel() {

    private val user = MutableLiveData<User>()

    private val _feedKey = MutableLiveData<String>()
    val feedKey: LiveData<String> = _feedKey


    private val _feedList = MutableLiveData<List<Feed>>()
    val feedList: LiveData<List<Feed>> = _feedList

    private val _feedData = MutableLiveData<List<FeedDataPoint>>()
    val feedData: LiveData<List<FeedDataPoint>> = _feedData

    fun setUser(newUser: User) {
        user.value = newUser
        getFeedList()
    }

    private fun getFeedList() {
        val user = user.value as User
        adafruitDataSource.getFeedsList(user.username, user.aioKey,
            {
                _feedList.postValue(it)
            }, {
                _feedList.postValue(emptyList())
            }
        )

    }

    fun setFeedKey(feedKey: String) {
        _feedKey.value = feedKey
    }

    fun getFeedData() {
        val feedKey = feedKey.value as String
        val user = user.value as User

        adafruitDataSource.getFeedData(
            user.username,
            user.aioKey,
            feedKey, { data ->
                val points = mutableListOf<FeedDataPoint>()
                data?.forEach {
                    points.add(
                        FeedDataPoint(
                            value = it.value.toFloat(),
                            created_at = it.created_at.time.toFloat()
                        )
                    )
                }
                _feedData.postValue(points)
            }, {
                _feedData.postValue(emptyList())
            }
        )
    }
}