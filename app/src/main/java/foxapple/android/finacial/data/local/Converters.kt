package foxapple.android.finacial.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    val gson = Gson()
    @TypeConverter
    fun stringToObject(value: String): List<Any> {
        val listType = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Any>): String {
        return gson.toJson(list)
    }
}