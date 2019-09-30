package fr.cdreyfus.airqualitysensorapp.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitDataSource
import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.User

class DataViewModel(val adafruitDataSource: AdafruitDataSource) : ViewModel() {

    private val user = MutableLiveData<User>()

    private val _feedKey = MutableLiveData<String>()
    val feedKey: LiveData<String> = _feedKey


    private val _feedList = MutableLiveData<List<Feed>>()
    val feeds: LiveData<List<Feed>> = _feedList

    fun setUser(newUser: User) {
        user.value = newUser
        getFeedList()
    }

    private fun getFeedList() {
        val user = user.value as User
        adafruitDataSource.getFeedsList(
            feedList = _feedList,
            username = user.username,
            aioKey = user.aioKey
        )

    }

    fun setFeedKey(feedKey: String) {
        _feedKey.value = feedKey
    }

    fun getFeedData() {
        val feedKey = feedKey.value as String
        val user = user.value as User

        adafruitDataSource.getFeedData(
            username = user.username,
            aioKey = user.aioKey,
            feedKey = feedKey
        )
    }
}