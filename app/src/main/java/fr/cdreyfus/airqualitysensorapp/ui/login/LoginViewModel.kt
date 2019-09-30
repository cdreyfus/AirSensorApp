package fr.cdreyfus.airqualitysensorapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(private val remoteUserDataSource: RemoteUserDataSource) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun login(key: String) {
        remoteUserDataSource.getUser(_user, key)
    }
}