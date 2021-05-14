package hu.dobszai.bookbrowse.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "favorites_table")
@JsonClass(generateAdapter = true)
class Book(
    @PrimaryKey val id: String,
    val volumeInfo : VolumeInfo,

    @Transient // indicates that field should be ignored by JSON
    @ColumnInfo(name = "favorite") var favorite: Boolean = false
)

class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val smallThumbnail: String,
    val thumbnail: String,
)
