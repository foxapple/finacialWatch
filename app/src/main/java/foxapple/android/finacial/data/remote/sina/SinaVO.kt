package foxapple.android.finacial.data.remote.sina

import java.io.Serializable

class SinaResponseVO : Serializable {
    val day: String = ""
    val open: Float = 0f
    val high: Float = 0f
    val low: Float = 0f
    val close: Float = 0f
    val volume: Int = 0

    override fun toString(): String {
        return "SinaResponseVO(day='$day', open=$open, high=$high, low=$low, close=$close, volume=$volume)"
    }
}