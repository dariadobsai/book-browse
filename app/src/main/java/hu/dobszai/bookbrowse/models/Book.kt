package hu.dobszai.bookbrowse.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "favorites_table")
@JsonClass(generateAdapter = true)
class Book(
    @PrimaryKey val id: String,
    @Embedded val volumeInfo : VolumeInfo,
)

// TODO
class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    //val smallThumbnail: String?,
    //val thumbnail: String?,
)


fun List<Book>.asDomainModel(): List<Book> {
    return map {
        Book(
            id = it.id,
            volumeInfo= it.volumeInfo,
        )
    }
}