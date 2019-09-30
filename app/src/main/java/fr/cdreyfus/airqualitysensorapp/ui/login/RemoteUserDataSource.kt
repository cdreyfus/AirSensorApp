package fr.cdreyfus.airqualitysensorapp.ui.login

import androidx.lifecycle.MutableLiveData
import fr.cdreyfus.airqualitysensorapp.core.AdafruitApiService
import fr.cdreyfus.airqualitysensorapp.core.plusAssign
import fr.cdreyfus.airqualitysensorapp.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class RemoteUserDataSource(private val adafruitApiService: AdafruitApiService) {

    private val subscription = CompositeDisposable()
    private val mutableLiveData = MutableLiveData<User>()

    fun getUser(aioKey: String): User? {
        var user: User? = null

        subscription += adafruitApiService.getUser(aioKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Timber.e(it)
            }
            .doOnSuccess {
                println(it)
            }
            .subscribe({ data ->
                println(data.string())
//                user = data
            }, {
                Timber.e(it)
            })
//        subscription.clear()

        return user
    }
}