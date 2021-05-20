package hu.dobszai.bookbrowse.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.makeramen.roundedimageview.RoundedImageView
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.BookViewModel


@BindingAdapter("title")
fun TextView.bindTitle(book: Book?) {
    book?.let {
        val title = it.volumeInfo.title
        this.text = if (title?.isNotEmpty() == true) title else "Unknown"
    }
}

@BindingAdapter("description")
fun TextView.bindDescription(book: Book?) {
    book?.let {
        val desc = it.volumeInfo.description
        this.text = if (desc?.isNotEmpty() == true) desc else ""
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

        val html = StringBuilder()
        html.append("<a href=").append(it.volumeInfo.previewLink).append(">view in browser</a>")

        this.apply {
            text = Html.fromHtml(html.toString(), Html.FROM_HTML_MODE_COMPACT)
            movementMethod = LinkMovementMethod.getInstance()

        }
    }
}

@BindingAdapter("favorite")
fun ImageButton.bindFav(viewModel: BookViewModel) {
    if (viewModel.isFavorite.value == true) {
        this.setImageResource(R.drawable.ic_favorite_filled)
    } else {
        this.setImageResource(R.drawable.ic_favorite_outlined)
    }
}


@BindingAdapter("cover")
fun RoundedImageView.bindCover(book: Book?) {
    book?.let {

        val transformation = RoundedTransformationBuilder()
            .cornerRadiusDp(4f)
            .oval(false)
            .build()

        Picasso.get()
            .load(book.volumeInfo.imageLinks?.smallThumbnail)
            .transform(transformation)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
    }
}

