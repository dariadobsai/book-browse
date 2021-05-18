package hu.dobszai.bookbrowse.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "favorites_table")
@JsonClass(generateAdapter = true)
data class Book(
    @PrimaryKey val id: String,
    @Embedded val volumeInfo: @RawValue VolumeInfo,
) : Parcelable

@Parcelize
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val previewLink: String,
    val publishedDate: String,
    val imageLinks: @RawValue ImageLinks?,
) : Parcelable

@Parcelize
data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
) : Parcelable

fun List<Book>.asDomainModel(): List<Book> {
    return map {
        Book(
            id = it.id,
            volumeInfo = it.volumeInfo,
        )
    }
}