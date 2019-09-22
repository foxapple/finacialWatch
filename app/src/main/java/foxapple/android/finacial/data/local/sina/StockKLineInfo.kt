package foxapple.android.finacial.data.local.sina

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import foxapple.android.finacial.data.local.tushare.StockBasicInfo

/**
 * @param ts_code='000001.SZ'
 * @param symbol='000001'
 * @param name='平安银行'
 * @param area='深圳'
 * @param industry='银行'
 * @param updateDate='2019-07-16 10:30:00'
 * @param min_5_data 5分钟线
 */
@Entity(tableName = "stock_kline_info")
@TypeConverters(KLineConverters::class)
data class StockKLineInfo(
    @PrimaryKey @ColumnInfo(name = "id") val ts_code: String,
    val symbol: String,
    val name: String,
    val area: String,
    val industry: String,
    val updateDate: Long,
    val min_5_data: MutableList<KLineDetailData>,
    val min_30_data: MutableList<KLineDetailData>,
    val min_60_data: MutableList<KLineDetailData>,
    val day_data: MutableList<KLineDetailData>
) {
    companion object {
        fun getKLineInfoFromBasicInfo(data: StockBasicInfo): StockKLineInfo {
            return StockKLineInfo(
                data.ts_code, data.symbol,
                data.name, data.area, data.industry, System.currentTimeMillis(),
                ArrayList(), ArrayList(), ArrayList(), ArrayList()
            )
        }
    }

    fun getKLineListByScale(scale: Int): MutableList<KLineDetailData> {
        return when (scale) {
            5 -> min_5_data
            30 -> min_30_data
            60 -> min_60_data
            240 -> day_data
            else -> ArrayList()
        }
    }

    override fun toString(): String {
        return "StockKLineInfo(ts_code='$ts_code', symbol='$symbol', name='$name', area='$area', industry='$industry', updateDate=$updateDate, min_5_data=$min_5_data, min_30_data=$min_30_data, min_60_data=$min_60_data, day_data=$day_data)"
    }

}