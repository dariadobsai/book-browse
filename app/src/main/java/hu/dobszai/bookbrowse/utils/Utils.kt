package hu.dobszai.bookbrowse.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
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


fun Context.displayToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG)

