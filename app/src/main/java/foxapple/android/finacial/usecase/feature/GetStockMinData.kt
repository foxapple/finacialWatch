package foxapple.android.finacial.usecase.feature

import foxapple.android.finacial.data.remote.Retrofit2Service
import foxapple.android.finacial.data.remote.sina.SinaResponseVO
import foxapple.android.finacial.usecase.Either
import foxapple.android.finacial.usecase.Failure
import foxapple.android.finacial.usecase.UseCase

class GetStockMinData : UseCase<List<SinaResponseVO>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<SinaResponseVO>> {
        val response =
            Retrofit2Service.SINA_API.getMinStorkData("sz002095", "60", "no").execute()
        if (response.isSuccessful) {
            response.body()
            return Either.Right(response.body())
        }
        return Either.Left(Failure.ServerError)
    }
}