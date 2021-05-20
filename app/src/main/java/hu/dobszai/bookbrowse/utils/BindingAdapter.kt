package hu.dobszai.bookbrowse.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.makeramen.roundedimageview.RoundedImageView
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import hu.dobszai.bookbrowse.R
import hu.dobszai.bookbrowse.models.Book
import hu.dobszai.bookbrowse.viewmodels.BookViewModel

val transformation: Transformation = RoundedTransformationBuilder()
    .cornerRadiusDp(4f)
    .oval(false)
    .build()

@BindingAdapter("title")
fun TextView.bindTitle(book: Book?) {
    val builder = StringBuilder()

    book?.let {
        val title = it.volumeInfo.title
        val publishedDate = it.volumeInfo.publishedDate
        if (title?.isNotEmpty() == true) {
            builder.append(title)
            this.visibility = View.VISIBLE

            if (publishedDate?.isNotEmpty() == true) {
                builder.append(" (").append(publishedDate).append(") ")
            }

            this.text = builder.toString()

        } else this.visibility = View.GONE
    }
}

@BindingAdapter("description")
fun TextView.bindDescription(book: Book?) {
    book?.let {
        val desc = it.volumeInfo.description
        if (desc?.isNotEmpty() == true) {
            this.apply {
                text = desc
                visibility = View.VISIBLE

            }
        } else this.visibility = View.GONE
    }
}

@BindingAdapter("authors")
fun TextView.bindAuthors(book: Book?) {
    val separator = ", "

    book?.let {
        val authors = it.volumeInfo.authors
        if (!authors.isNullOrEmpty()) {
            this.apply {
                text = authors.joinToString(separator)
                visibility = View.VISIBLE

            }
        } else this.visibility = View.GONE
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


@BindingAdapter("thumbnail")
fun RoundedImageView.bindThumbnail(book: Book?) {
    book?.let {

        Picasso.get()
            .load(book.volumeInfo.imageLinks?.smallThumbnail)
            .transform(transformation)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
    }
}

@BindingAdapter("cover")
fun ImageView.bindCover(book: Book?) {
    book?.let {

        Picasso.get()
            .load(book.volumeInfo.imageLinks?.thumbnail)
            .transform(transformation)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
    }
}

