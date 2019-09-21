package foxapple.android.finacial.data.local.sina

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters to allow Room to reference complex data types.
 */
class KLineConverters {
    val gson = Gson()
    @TypeConverter
    fun stringToObject(value: String): List<KLineDetailData> {
        val listType = object : TypeToken<List<KLineDetailData>>() {

        }.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<KLineDetailData>): String {
        return gson.toJson(list)
    }
}