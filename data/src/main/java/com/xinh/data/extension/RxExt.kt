package com.xinh.data.extension

import com.xinh.data.base.BaseResponse
import com.xinh.domain.exception.Failure
import com.xinh.domain.functional.Either
import io.reactivex.Observable
import retrofit2.Response

fun <T, R> Observable<Response<BaseResponse<T>>>.toEither(transformer: (T) -> R): Observable<Either<Failure, R>> {
    return map {
        if (it.isSuccessful) {
            val body = it.body()

            if (body?.data != null && body.status == true) {

                Either.Right(transformer(body.data!!))

            } else if (body?.status == false) {

                Either.Left(Failure.ServerError(body.error))

            } else {

                Either.Left(Failure.ServerError())

            }
        } else {
            Either.Left(Failure.ServerError())
        }
    }
}