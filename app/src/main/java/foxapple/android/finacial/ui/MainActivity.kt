package foxapple.android.finacial.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import foxapple.android.finacial.R
import foxapple.android.finacial.data.local.tushare.StockBasicInfoRepository
import foxapple.android.finacial.usecase.UseCase
import foxapple.android.finacial.usecase.feature.GetStockBasicInfo
import foxapple.android.finacial.usecase.feature.GetStockKLineData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            GetStockBasicInfo().invoke(UseCase.None()) {
                it.success { list ->
                    GetStockKLineData(list[0]).invoke(UseCase.None()) { kLine ->
                        kLine.success { kLineInfo ->
                            Log.d(tag, kLineInfo.toString())
                        }
                    }
                }
            }
        }

        clear_btn.setOnClickListener {
            stock_basic.text = ""
            GlobalScope.launch(Dispatchers.IO) {
                StockBasicInfoRepository.getInstance().clear()
            }
        }
    }

    private fun getBasicStockInfo() {
        GlobalScope.launch {
            val result = withContext(Dispatchers.IO) { GetStockBasicInfo().run(UseCase.None()) }
            withContext(Dispatchers.Main) {
                result.either(
                    { Toast.makeText(applicationContext, "获取数据失败", Toast.LENGTH_LONG) },
                    { stockInfo ->
                        val sb = StringBuilder()
                        stockInfo.forEach {
                            sb.appendln(it.toString())
                        }
                        stock_basic.text = sb.toString()
                        ""
                    })
            }
        }
    }

}
