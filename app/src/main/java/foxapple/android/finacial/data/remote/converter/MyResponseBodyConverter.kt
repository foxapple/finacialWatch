package foxapple.android.finacial.data.remote.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.StringReader

class MyResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody?): T {
        var json = verify(value?.string())
        val jsonReader = gson.newJsonReader(StringReader(json))
        value.use {
            return adapter.read(jsonReader)
        }
    }

    fun verify(json: String?): String {
        if (json == null) return ""
        return json.replace("day", "\"day\"")
            .replace("open", "\"open\"")
            .replace("high", "\"high\"")
            .replace("low", "\"low\"")
            .replace("close", "\"close\"")
            .replace("volume", "\"volume\"")
    }
}