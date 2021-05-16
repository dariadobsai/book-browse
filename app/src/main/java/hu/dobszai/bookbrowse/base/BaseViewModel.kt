package hu.dobszai.bookbrowse.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.base.NavigationCommand
import hu.dobszai.bookbrowse.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()

    val isOnline = MutableLiveData(false)

    protected val viewModelScope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}