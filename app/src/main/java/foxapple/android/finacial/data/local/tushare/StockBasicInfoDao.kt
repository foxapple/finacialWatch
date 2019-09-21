package foxapple.android.finacial.data.local.tushare

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy



/**
 * The Data Access Object for the [StockBasicInfo] class.
 */
@Dao
interface StockBasicInfoDao {
    @Query("SELECT * FROM stock_basic_info")
    fun getAllStorks(): List<StockBasicInfo>

    @Query("DELETE FROM stock_basic_info")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockBasicInfo(stockBasicInfo: StockBasicInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStock(stockBasicInfoList: List<StockBasicInfo>)
}