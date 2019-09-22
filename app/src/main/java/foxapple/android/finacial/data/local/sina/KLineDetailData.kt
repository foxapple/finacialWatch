package foxapple.android.finacial.data.local.sina

import java.io.Serializable

/**
 * @param open 开盘价
 * @param high 最高
 * @param low 最低
 * @param close 收盘
 * @param volume 交易量
 * @param ma 均值
 * @param macd MACD
 */
class KLineDetailData(
    val date: Long,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Int,
    var ma: HashMap<Int, Float>,
    var macd: MACD
) : Serializable {
    override fun toString(): String {
        return "\nKLineDetailData(date=$date, open=$open, high=$high, low=$low, close=$close, volume=$volume, ma=$ma, macd=$macd)"
    }
}

enum class MovingAverageData(val average: Int) {
    MA_5(5),
    MA_13(13),
    MA_21(21),
    MA_55(55),
    MA_89(89),
    MA_144(144)
}

class MACD(
    
) : Serializable