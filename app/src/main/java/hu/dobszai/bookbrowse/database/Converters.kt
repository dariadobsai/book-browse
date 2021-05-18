package hu.dobszai.bookbrowse.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import hu.dobszai.bookbrowse.models.ImageLinks

object Converters {

    @TypeConverter
    fun fromString(str: String?): List<String>? {
        return str?.split(",")?.map { it }
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun imgToString(app: ImageLinks): String = Gson().toJson(app)

    @TypeConverter
    fun stringToImg(string: String): ImageLinks = Gson().fromJson(string, ImageLinks::class.java)
}