package hu.dobszai.bookbrowse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.dobszai.bookbrowse.utils.hideKeyboard

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideKeyboard(this)
    }
}