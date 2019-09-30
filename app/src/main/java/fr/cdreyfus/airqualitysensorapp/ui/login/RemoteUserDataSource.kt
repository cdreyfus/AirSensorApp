package fr.cdreyfus.airqualitysensorapp.ui.login

import androidx.lifecycle.MutableLiveData
import fr.cdreyfus.airqualitysensorapp.core.service.AdafruitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RemoteUserDataSource(private val adafruitApiService: AdafruitApiService) {

    fun getUser(user: MutableLiveData<User>, aioKey: String) {
        adafruitApiService.getUser(aioKey).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Timber.e(t)
                user.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                user.postValue(response.body()?.user)
            }
        })
    }
}