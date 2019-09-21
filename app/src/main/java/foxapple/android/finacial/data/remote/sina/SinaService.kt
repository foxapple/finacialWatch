package foxapple.android.finacial.data.remote.sina

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SinaService {
    @GET("CN_MarketData.getKLineData")
    fun getMinStorkData(
        @Query("symbol") symbol: String,
        @Query("scale") scale: String,
        @Query("ma") ma: String,
        @Query("datalen") len: String = "1023"
    ): Call<List<SinaResponseVO>>
}