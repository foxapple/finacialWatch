package foxapple.android.finacial.data

import foxapple.android.finacial.data.local.tushare.StockBasicInfo

/**
 * Created by lizhe on 2021/10/26
 */


object ETFOriginData {
    private val ETFDataList: List<StockBasicInfo>

    init {
        val list = mutableListOf<StockBasicInfo>(
            StockBasicInfo(
                "510500.SH",
                "sh510500",
                "中证500",
                "上海",
                "中小企业",
                "上海",
                ""
            ),
            StockBasicInfo(
                "510300.SH",
                "sh510300",
                "沪深300"
            ),
            StockBasicInfo(
                "512010.SH",
                "sh512010",
                "医药"
            ),
            StockBasicInfo(
                "512200.SH",
                "sh512200",
                "地产"
            ),
            StockBasicInfo(
                "515700.SH",
                "sh515700",
                "中概互联"
            ),
            StockBasicInfo(
                "515700.SH",
                "sh515700",
                "新能源"
            ),
            StockBasicInfo(
                "515790.SH",
                "sh515790",
                "光伏"
            ),
            StockBasicInfo(
                "159928.SZ",
                "sz159928",
                "消费"
            )
        )

        ETFDataList = list.toList()
    }

    fun getETFDataList() = ETFDataList
}