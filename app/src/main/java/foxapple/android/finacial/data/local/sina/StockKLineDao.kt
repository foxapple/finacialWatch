package foxapple.android.finacial.data.local.sina

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * The Data Access Object for the [StockKLineInfo] class.
 */
@Dao
interface StockKLineDao {
    @Query("SELECT * FROM stock_kline_info WHERE id LIKE :id LIMIT 1")
    fun getStockById(id: String): StockKLineInfo?

    @Query("SELECT * FROM stock_kline_info")
    fun getAllStorks(): List<StockKLineInfo>

    @Query("DELETE FROM stock_kline_info")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockBasicInfo(stockBasicInfo: StockKLineInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStock(stockBasicInfoList: List<StockKLineInfo>)
}