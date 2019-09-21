package foxapple.android.finacial.data.local.tushare

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 返回参数 case
 * @param ts_code='000001.SZ'
 * @param symbol='000001'
 * @param name='平安银行'
 * @param area='深圳'
 * @param industry='银行'
 * @param market='主板'
 * @param list_date='19910403'
 */
@Entity(tableName = "stock_basic_info")
data class StockBasicInfo(
    @PrimaryKey @ColumnInfo(name = "id") val ts_code: String,
    val symbol: String,
    val name: String,
    val area: String,
    val industry: String,
    val market: String,
    val list_date: String
) {
    override fun toString(): String {
        return "StockBasicInfo(ts_code='$ts_code', symbol='$symbol', name='$name', area='$area', industry='$industry', market='$market', list_date='$list_date')"
    }
}

fun getStockBasicInfoFromStringList(fields: List<String>, datas:List<String>): StockBasicInfo {
    return StockBasicInfo(
        datas.getOrNull(fields.indexOf("ts_code")) ?: "",
        datas.getOrNull(fields.indexOf("symbol")) ?: "",
        datas.getOrNull(fields.indexOf("name")) ?: "",
        datas.getOrNull(fields.indexOf("area")) ?: "",
        datas.getOrNull(fields.indexOf("industry")) ?: "",
        datas.getOrNull(fields.indexOf("market")) ?: "",
        datas.getOrNull(fields.indexOf("list_date")) ?: ""
    )
}