package foxapple.android.finacial.usecase.feature

import android.util.ArrayMap
import android.util.Log
import foxapple.android.finacial.data.local.tushare.StockBasicInfo
import foxapple.android.finacial.data.local.tushare.StockBasicInfoRepository
import foxapple.android.finacial.data.local.tushare.getStockBasicInfoFromStringList
import foxapple.android.finacial.data.remote.Retrofit2Service
import foxapple.android.finacial.data.remote.tushare.TuShareVO
import foxapple.android.finacial.usecase.Either
import foxapple.android.finacial.usecase.Failure
import foxapple.android.finacial.usecase.UseCase

class GetStockBasicInfo : UseCase<List<StockBasicInfo>, UseCase.None>() {
    val tag = "GetStockBasicInfo"

    override suspend fun run(params: None): Either<Failure, List<StockBasicInfo>> {
        val localData = StockBasicInfoRepository.getInstance().getStockBasicInfos()
        if (localData.isEmpty()) {
            return getStockInfoRemote()
        }
        return Either.Right(localData)
    }

    private fun getStockInfoRemote(): Either<Failure, List<StockBasicInfo>> {
        StockBasicInfoRepository.getInstance().clear()
        val data = TuShareVO()
        data.api_name = "stock_basic"
        val param = ArrayMap<String, String>()
        param["list_status"] = "L"
        data.params = param
        data.fields = ""
        val call = Retrofit2Service.TU_SHARE_API.getStorkData("", data)
        val response = call.execute()
        if (response.isSuccessful) {
            val tuShareResponseVO = response.body()
            if (tuShareResponseVO.code == "0" && tuShareResponseVO.data != null) {
                val field = tuShareResponseVO.data!!.fields!!
                val result = tuShareResponseVO.data!!.items!!.map { getStockBasicInfoFromStringList(field, it) }.sortedBy { it.symbol.toInt() }
                StockBasicInfoRepository.getInstance().insetyStock(result)
                Log.d(tag, StockBasicInfoRepository.getInstance().getStockBasicInfos().take(10).toString())
                return Either.Right(result)
            }
        }
        return Either.Left(Failure.ServerError)
    }
}