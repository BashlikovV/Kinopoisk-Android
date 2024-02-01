package by.bashlikovvv.moviesdata.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LongListTypeConverter {

    @TypeConverter
    fun fromList(value: List<Long>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromString(value: String): List<Long> {
        val gson = Gson()
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, type)
    }

}