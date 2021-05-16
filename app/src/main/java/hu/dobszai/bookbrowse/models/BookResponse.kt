package hu.dobszai.bookbrowse.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    val items: List<Book>,
    val totalItems: Int
)

fun BookResponse.asDatabaseModel(): Array<Book> {
    return items.map {
        Book(
            id = it.id,
            volumeInfo = it.volumeInfo,
            favorite = it.favorite
        )
    }.toTypedArray()
}

