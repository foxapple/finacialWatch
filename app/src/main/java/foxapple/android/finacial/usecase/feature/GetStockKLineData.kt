package foxapple.android.finacial.usecase.feature

import foxapple.android.finacial.data.local.AppDatabase
import foxapple.android.finacial.data.local.sina.KLineDetailData
import foxapple.android.finacial.data.local.sina.MACD
import foxapple.android.finacial.data.local.sina.StockKLineInfo
import foxapple.android.finacial.data.local.sina.StockKLineInfoRepository
import foxapple.android.finacial.data.local.tushare.StockBasicInfo
import foxapple.android.finacial.data.remote.Retrofit2Service
import foxapple.android.finacial.data.remote.sina.SinaResponseVO
import foxapple.android.finacial.usecase.Either
import foxapple.android.finacial.usecase.Failure
import foxapple.android.finacial.usecase.UseCase
import foxapple.android.finacial.utilities.DateString2TimeStamp
import foxapple.android.finacial.utilities.isToday

val AvailableMinData = listOf(5, 30, 60, 240)

/**
 * @param symbol='000001.SZ'
 */
class GetStockKLineData(private val stock: StockBasicInfo) : UseCase<StockKLineInfo, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, StockKLineInfo> {
        val data = StockKLineInfoRepository.getInstance().getStockKLineInfoById(stock.ts_code)
        return if (data == null || !isToday(data.updateDate)) {
            val stockCode = stock.ts_code.split('.')
            val apiSymbol = stockCode[1].toLowerCase() + stockCode[0]
            val kLineInfo = StockKLineInfo.getKLineInfoFromBasicInfo(stock)
            val result = getKLineDataRemote(apiSymbol, kLineInfo)
            if(result.isRight) {
                StockKLineInfoRepository.getInstance().insertStock((result as Either.Right).b)
            }
            return result
        } else {
            Either.Right(data)
        }
    }

    private fun getKLineDataRemote(apiSymbol: String, kLineInfo: StockKLineInfo): Either<Failure, StockKLineInfo> {
        AvailableMinData.forEach { scale ->
            val response = Retrofit2Service.SINA_API.getMinStorkData(apiSymbol, scale.toString(), "no").execute()
            if (response.isSuccessful) {
                val data = response.body()
                data.forEach { singleDay ->
                    val kLine = transSinaRespond2KLineDetailData(singleDay)
                    kLineInfo.getKLineListByScale(scale).add(kLine)
                }
            } else {
                return Either.Left(Failure.ServerError)
            }
        }
        return Either.Right(kLineInfo)
    }

    private fun transSinaRespond2KLineDetailData(response: SinaResponseVO): KLineDetailData {
        return KLineDetailData(
            DateString2TimeStamp(response.day), response.open, response.high, response.low, response.close,
            response.volume, HashMap(), MACD(0f,0f,0f,0f)
        )
    }
}