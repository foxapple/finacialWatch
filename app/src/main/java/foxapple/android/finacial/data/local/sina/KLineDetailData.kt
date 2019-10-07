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

/**
 *  12日EMA＝2÷13×（今天收盘指数一昨天的指数平均值）＋昨天的指数平均值。
 *  26日EMA＝（2÷27）×今天收盘指数＋（25÷27）×昨天的指数平均值。
 *  DIFF=今日EMA（12）- 今日EMA（26）
 *  DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
 *             = DIFF 的 9日EMA
 */
class MACD(
    val EMA_12: Float, val EMA_26: Float, val DIFF: Float, val DEA: Float
) : Serializable {
    val macd = (DIFF - DEA) * 2f
    override fun toString(): String {
        return "MACD(DIFF=$DIFF, DEA=$DEA, MACD=$macd)"
    }
}