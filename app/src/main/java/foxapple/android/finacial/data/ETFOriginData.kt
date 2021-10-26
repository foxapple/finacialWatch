package foxapple.android.finacial.data

import foxapple.android.finacial.data.local.tushare.StockBasicInfo

/**
 * Created by lizhe on 2021/10/26
 */


object ETFOriginData {
    private val ETFDataList: List<StockBasicInfo>

    init {
        val list = mutableListOf<StockBasicInfo>()
        list.add(
            StockBasicInfo(
                "510500.SH",
                "sh510500",
                "中证500",
                "上海",
                "中小企业",
                "上海",
                ""
            )
        )

        ETFDataList = list.toList()
    }

    fun getETFDataList() = ETFDataList
}