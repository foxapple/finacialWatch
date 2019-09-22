package foxapple.android.finacial.utilities

import java.text.SimpleDateFormat
import java.util.*

fun DateString2TimeStamp(time: String, format: String = "yyyy-MM-dd HH:mm:ss", dayFormat: String = "yyyy-MM-dd"): Long {
    var inputsdf: SimpleDateFormat = SimpleDateFormat(if (time.contains(':')) { format } else { dayFormat })
    var date: Date = inputsdf.parse(time)
    return date.time
}

fun isToday(time: Long): Boolean {
    return (Date().time - time) < 12 * 60 * 60 * 1000
}