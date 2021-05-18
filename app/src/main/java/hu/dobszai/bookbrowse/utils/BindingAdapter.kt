package hu.dobszai.bookbrowse.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

@BindingAdapter("title")
fun TextView.bindTitle(book: Book?) {
    book?.let {
        val title = it.volumeInfo.title
        this.text = if (title.isNotEmpty()) title else "Unknown"
    }
}

@BindingAdapter("authors")
fun TextView.bindAuthors(book: Book?) {
    val separator = ", "
    book?.let {
        val authors = it.volumeInfo.authors
        this.text = if (!authors.isNullOrEmpty()) authors.joinToString(separator) else "Unknown"
    }
}

@BindingAdapter("url")
fun TextView.bindLink(book: Book?) {
    book?.let {
        this.text = Html.fromHtml(it.volumeInfo.previewLink, Html.FROM_HTML_MODE_COMPACT)
        this.movementMethod = LinkMovementMethod.getInstance()
    }
}

@BindingAdapter("favorite")
fun ImageButton.bindFav(viewModel: BookViewModel) {
    if(viewModel.isFavorite.value == true){
        this.setImageResource(R.drawable.ic_favorite_filled)
    }else{
        this.setImageResource(R.drawable.ic_favorite_outlined)
    }
}


/*
@BindingAdapter("cover")
fun TextView.bindCover(book: Book?) {
    book?.let {
        if () {
            Glide.get()
                .load(it.url)
                .placeholder(R.drawable.animation_ic_loading)
                // This image is also triggered when the work is triggered on the background, since the image is never downloaded on the background
                .error(R.drawable.image_error)
                .into(this)
        } else this.setImageResource(R.drawable.empty_picture_of_day)
    }
}
*/
