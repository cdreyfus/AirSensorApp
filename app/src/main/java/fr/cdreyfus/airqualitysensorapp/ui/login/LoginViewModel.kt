package fr.cdreyfus.airqualitysensorapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitDataSource
import fr.cdreyfus.airqualitysensorapp.model.User
import timber.log.Timber

class LoginViewModel(private val adafruitDataSource: AdafruitDataSource) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun login(key: String) {
        adafruitDataSource.getUser(key, {
            _user.postValue(it?.user?.copy(aioKey = key))
        }, {
            _user.postValue(null)
            Timber.e(it)
        })
    }
}