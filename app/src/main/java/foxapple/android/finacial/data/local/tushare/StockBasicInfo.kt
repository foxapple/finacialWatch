package foxapple.android.finacial.data.local.tushare

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    override fun toString() = "$symbol  $name"
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