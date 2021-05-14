package hu.dobszai.bookbrowse.utils

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



