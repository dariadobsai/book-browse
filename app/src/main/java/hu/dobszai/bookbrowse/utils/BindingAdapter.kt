package hu.dobszai.bookbrowse.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import hu.dobszai.bookbrowse.models.Book

@BindingAdapter("title")
fun TextView.bindTitle(book: Book?) {
    book.let {
        this.text = it?.volumeInfo?.title
    }

}

@BindingAdapter("authors")
fun TextView.bindAuthors(book: Book?) {
    val separator = ", "
    book.let {
        this.text = book?.volumeInfo?.authors?.joinToString(separator)
    }
}
