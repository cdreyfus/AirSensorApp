package fr.cdreyfus.airqualitysensorapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.cdreyfus.airqualitysensorapp.model.User

class LoginViewModel(private val remoteUserDataSource: RemoteUserDataSource) : ViewModel() {


    // Create a LiveData with a String
    private val aioKey: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val user: MutableLiveData<User> by lazy { MutableLiveData<User>() }


    fun login(aioKey: String) {
        this.aioKey.value = aioKey

        val newUser = remoteUserDataSource.getUser(aioKey)
        user.postValue(newUser)

    }
}