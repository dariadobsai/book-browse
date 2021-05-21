package hu.dobszai.bookbrowse.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import hu.dobszai.bookbrowse.R

val appBarConfiguration = AppBarConfiguration(
    setOf(
        R.id.browseFragment,
        R.id.detailsFragment,
        R.id.favoritesFragment
    )
)

fun AppCompatActivity.disableAppBarTitle() =
    this.supportActionBar?.setDisplayShowTitleEnabled(false)

fun closeKeyboard(activity: AppCompatActivity?) {
    val view = activity?.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Hide keyboard on application start and
 * prevent it from pushing the content.
 *
 * @param activity
 */
fun hideKeyboard(activity: AppCompatActivity) {
    activity.window.apply {
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}

fun View.showSnackBar(str: String) {
    Snackbar.make(
        this,
        str,
        Snackbar.LENGTH_LONG
    ).show()
}
