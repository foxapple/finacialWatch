package foxapple.android.finacial.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import foxapple.android.finacial.R
import foxapple.android.finacial.data.ETFInfoData
import foxapple.android.finacial.data.ETFOriginData
import foxapple.android.finacial.data.local.tushare.StockBasicInfoRepository
import foxapple.android.finacial.usecase.UseCase
import foxapple.android.finacial.usecase.feature.ComputeMAData
import foxapple.android.finacial.usecase.feature.GetStockBasicInfo
import foxapple.android.finacial.usecase.feature.GetStockKLineData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ln
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            fetchAllETFData()
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

    private fun fetchAllETFData() {
        val etfList = mutableListOf<ETFInfoData>()
        ETFOriginData.getETFDataList().forEach { stockInfo ->
            GetStockKLineData(stockInfo).invokeWithSuccess(UseCase.None()) { kLine ->
                ComputeMAData().invokeWithSuccess(kLine)
                val max = kLine.day_data.maxBy { it.high }!!.high
                val min = kLine.day_data.minBy { it.low }!!.low
                val current = kLine.day_data.first().close
                val position = (ln(current) - ln(min)) / (ln(max) - ln(min)) * 100
                // 修正建议函数，在最低位时取 100%的仓位  y = 0.0001* x^3 - 0.005 * x^2 + 0.5 * x - 1.2
                val suggest = 0.00010 * position.pow(3) - 0.005 * position.pow(2) + 0.5 * position - 1.2
                val etfInfoData = ETFInfoData(
                    max, min, current, suggest.toFloat(), position,
                    stockInfo
                )
                etfList.add(etfInfoData)
            }
        }
    }
}
