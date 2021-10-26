package foxapple.android.finacial.data.remote

import com.google.gson.Gson
import foxapple.android.finacial.data.remote.converter.MyConverterFactory
import foxapple.android.finacial.data.remote.sina.SinaService
import foxapple.android.finacial.data.remote.tushare.TuShareService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit2Service {
    val TU_SHARE_API: TuShareService = Retrofit.Builder()
        .baseUrl("https://api.tushare.pro")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TuShareService::class.java)

    val SINA_API: SinaService = Retrofit.Builder()
        .baseUrl("https://money.finance.sina.com.cn/quotes_service/api/json_v2.php/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(SinaService::class.java)
}