package hu.dobszai.bookbrowse.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    val items: List<Book>,
    val totalItems: Int
)