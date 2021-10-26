package foxapple.android.finacial.data

import foxapple.android.finacial.data.local.tushare.StockBasicInfo

data class ETFInfoData(
    val max: Float,
    val min: Float,
    val current: Float,
    val suggest: Float,
    val stock: StockBasicInfo
)