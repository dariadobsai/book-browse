package hu.dobszai.bookbrowse.utils.network

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/**
 * Original source:
 * https://github.com/mitchtabian/food2fork-compose/blob/master/app/src/main/java/com/codingwithmitch/food2forkcompose/presentation/util/ConnectivityManager.kt
 */
class ConnectivityManager
constructor(val application: Application) {
    private val connectionLiveData = ConnectionLiveData(application)

    val isNetworkAvailable = MutableLiveData(true)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let {
                isNetworkAvailable.value = it
            }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}