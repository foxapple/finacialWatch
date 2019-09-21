package foxapple.android.finacial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import foxapple.android.finacial.data.local.tushare.StockBasicInfo
import foxapple.android.finacial.data.local.tushare.StockBasicInfoDao
import foxapple.android.finacial.utilities.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(entities = [StockBasicInfo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storkBasicInfoDao(): StockBasicInfoDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}