package foxapple.android.finacial.data.remote.tushare

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface TuShareService {
    @POST
    fun getStorkData(@Url url: String, @Body post: TuShareVO): Call<TuShareResponseVO>
}