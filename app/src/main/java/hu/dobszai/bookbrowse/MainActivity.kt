package hu.dobszai.bookbrowse

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import hu.dobszai.bookbrowse.utils.hideKeyboard
import hu.dobszai.bookbrowse.utils.network.ConnectivityManager
import hu.dobszai.bookbrowse.utils.showSnackBar
import org.koin.android.ext.android.inject

/**
 * Created by Daria Dobszai
 */
class MainActivity : AppCompatActivity() {

    private val connectivityManager: ConnectivityManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideKeyboard(this)

        val view = findViewById<View>(R.id.context_view)
        connectivityManager.isNetworkAvailable.observe(this, { hasInternet ->
            if (hasInternet) view.showSnackBar(getString(R.string.sb_online))
            else view.showSnackBar(getString(R.string.sb_no_interent))
        })
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }
}