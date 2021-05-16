package hu.dobszai.bookbrowse.data

import androidx.room.TypeConverter

object Converters {

    @TypeConverter
    fun fromString(str: String?): List<String>? {
        return str?.split(",")?.map { it }
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }
}