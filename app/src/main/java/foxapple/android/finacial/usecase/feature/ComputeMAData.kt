package foxapple.android.finacial.usecase.feature

import foxapple.android.finacial.data.local.sina.*
import foxapple.android.finacial.usecase.Either
import foxapple.android.finacial.usecase.Failure
import foxapple.android.finacial.usecase.UseCase

class ComputeMAData : UseCase<StockKLineInfo, StockKLineInfo>() {
    companion object {
        var isRunning = false
    }

    override suspend fun run(data: StockKLineInfo): Either<Failure, StockKLineInfo> {
        if (isRunning) return Either.Left(Failure.ServerError)
        isRunning = true
        AvailableMinData.forEach { min ->
            val kLineList = data.getKLineListByScale(min)
            kLineList.sortBy { it.date }
            computeMAData(kLineList)
            computeMACDData(kLineList)
        }
        StockKLineInfoRepository.getInstance().insertStock(data)
        isRunning = false
        return Either.Right(data)
    }

    private fun computeMAData(kLineList: MutableList<KLineDetailData>) {
        MovingAverageData.values().forEach { average ->
            average.average
            var sum = 0f
            kLineList.forEachIndexed { index, kLineDetailData ->
                sum += kLineDetailData.close
                sum -= (kLineList.getOrNull(index - average.average)?.close ?: 0f)
                if (index >= average.average - 1) {
                    kLineDetailData.ma[average.average] = sum / average.average
                }
            }
        }
    }

    /**
     *  12日EMA＝2÷13×（今天收盘指数一昨天的指数平均值）＋昨天的指数平均值。
     *  26日EMA＝（2÷27）×今天收盘指数＋（25÷27）×昨天的指数平均值。
     *  DIFF=今日EMA（12）- 今日EMA（26）
     *  DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
     *             = DIFF 的 9日EMA
     */
    private fun computeMACDData(kLineList: MutableList<KLineDetailData>) {
        var lastMACD = MACD(0f, 0f, 0f, 0f)
        kLineList.forEachIndexed { index, kLineDetailData ->
            if (index == 0) {
                kLineDetailData.macd = MACD(0f, 0f, 0f, 0f)
                lastMACD = MACD(kLineDetailData.close, kLineDetailData.close, 0f, 0f)
            } else {
                val mea_12 = 2f / 13f * (kLineDetailData.close - lastMACD.EMA_12) + lastMACD.EMA_12
                val mea_26 = 2f / 27f * (kLineDetailData.close - lastMACD.EMA_26) + lastMACD.EMA_26
                val diff = mea_12 - mea_26
                val dea = 2f / 10f * (diff - lastMACD.DEA) + lastMACD.DEA
                lastMACD = MACD(mea_12, mea_26, diff, dea)
                kLineDetailData.macd = lastMACD
            }
        }
    }
}