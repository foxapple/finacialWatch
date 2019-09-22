package foxapple.android.finacial.usecase.feature

import foxapple.android.finacial.data.local.sina.KLineDetailData
import foxapple.android.finacial.data.local.sina.MovingAverageData
import foxapple.android.finacial.data.local.sina.StockKLineInfo
import foxapple.android.finacial.data.local.sina.StockKLineInfoRepository
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
        AvailableMinData.forEach {
            val kLineList = data.getKLineListByScale(it)
            computeMAData(kLineList)
        }
        StockKLineInfoRepository.getInstance().insertStock(data)
        isRunning = false
        return Either.Right(data)
    }

    private fun computeMAData(kLineList: MutableList<KLineDetailData>) {
        kLineList.sortBy { it.date }
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
}