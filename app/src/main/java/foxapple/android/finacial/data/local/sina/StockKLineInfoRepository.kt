package foxapple.android.finacial.data.local.sina

import foxapple.android.finacial.MainApplication
import foxapple.android.finacial.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StockKLineInfoRepository private constructor(private val stockKLineDao: StockKLineDao) {
    @Synchronized
    fun clear() {
        stockKLineDao.clear()
    }

    @Synchronized
    fun getStockKLineInfoById(id: String) = stockKLineDao.getStockById(id)

    @Synchronized
    fun getStockKLineInfos() = stockKLineDao.getAllStorks()

    @Synchronized
    suspend fun insertStock(data: StockKLineInfo) {
        stockKLineDao.insertStockBasicInfo(data)
    }

    @Synchronized
    fun insetyStock(list: List<StockKLineInfo>) {
        GlobalScope.launch(Dispatchers.IO) { stockKLineDao.insertAllStock(list) }
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: StockKLineInfoRepository? = null

        fun getInstance(): StockKLineInfoRepository {
            val stockKLineDao = AppDatabase.getInstance(MainApplication.instance).stockKLineDao()
            return instance ?: synchronized(this) {
                instance ?: StockKLineInfoRepository(stockKLineDao).also { instance = it }
            }
        }
    }
}