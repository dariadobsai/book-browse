package hu.dobszai.bookbrowse.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import hu.dobszai.bookbrowse.models.Book


@BindingAdapter("title")
fun TextView.bindTitle(book: Book?) {
    book.let {
        it?.volumeInfo?.title
    }
}

@BindingAdapter("authors")
fun TextView.bindAuthors(book: Book?) {
    val separator = ", "
    book.let {
        it?.volumeInfo?.authors?.joinToString(separator)
    }
}
