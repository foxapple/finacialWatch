package foxapple.android.finacial.usecase.algorithm

import foxapple.android.finacial.data.local.sina.KLineDetailData

abstract class BaseComputeMethod(val kLineData: List<KLineDetailData>) {
    // 是否具有特征
    abstract fun isPattern(): Boolean
}