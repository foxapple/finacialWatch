package foxapple.android.finacial.data.local.tushare

import foxapple.android.finacial.MainApplication
import foxapple.android.finacial.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StockBasicInfoRepository private constructor(private val stockBasicInfoDao: StockBasicInfoDao) {
    @Synchronized
    fun clear() {
        stockBasicInfoDao.clear()
    }

    @Synchronized
    fun getStockBasicInfos() = stockBasicInfoDao.getAllStorks()

    @Synchronized
    fun insertStock(data: StockBasicInfo) {
        GlobalScope.launch(Dispatchers.IO) { stockBasicInfoDao.insertStockBasicInfo(data) }
    }

    @Synchronized
    fun insetyStock(list: List<StockBasicInfo>) {
        GlobalScope.launch(Dispatchers.IO) { stockBasicInfoDao.insertAllStock(list) }
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: StockBasicInfoRepository? = null

        fun getInstance(): StockBasicInfoRepository {
            val stockBasicInfoDao = AppDatabase.getInstance(MainApplication.instance).storkBasicInfoDao()
            return instance ?: synchronized(this) {
                instance ?: StockBasicInfoRepository(stockBasicInfoDao).also { instance = it }
            }
        }
    }
}