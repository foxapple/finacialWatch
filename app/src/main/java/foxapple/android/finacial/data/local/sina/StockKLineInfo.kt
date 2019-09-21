package foxapple.android.finacial.data.local.sina

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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
    val updateDate: String,
    val min_5_data: List<KLineDetailData>,
    val min_30_data: List<KLineDetailData>,
    val min_60_data: List<KLineDetailData>,
    val min_90_data: List<KLineDetailData>,
    val day_data: List<KLineDetailData>,
    val week_data: List<KLineDetailData>,
    val month_data: List<KLineDetailData>
) {
    override fun toString() = "$symbol  $name"
}