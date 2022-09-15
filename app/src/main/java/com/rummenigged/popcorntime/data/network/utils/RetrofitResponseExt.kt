package com.rummenigged.popcorntime.data.network.utils

import com.rummenigged.popcorntime.common.ErrorResponse
import com.rummenigged.popcorntime.common.Outcome
import retrofit2.Response
import java.net.HttpURLConnection

fun <R: Any> Response<R>.parseResponse(): Outcome<R> =
    if (isSuccessful){
        body()?.let {
            Outcome.Success(value = it)
        } ?: Outcome.Failure(
            statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR
        )
    }else{
        parseErrorBody(
            code = code(),
            error = errorBody()?.toString() ?: ""
        )
    }

internal fun parseErrorBody(
    code: Int,
    error: String
) = try {
    Outcome.Failure(
        statusCode = code,
        errorResponse = ErrorResponse(error)
    )
}catch (e: Exception){
    Outcome.Failure(
        statusCode = HttpURLConnection.HTTP_INTERNAL_ERROR
    )
}