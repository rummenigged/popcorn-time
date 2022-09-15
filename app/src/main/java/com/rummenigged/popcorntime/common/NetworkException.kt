package com.rummenigged.popcorntime.common

import java.net.HttpURLConnection

sealed class NetworkException(
    message: String? = null,
    throwable: Throwable? = null
): Exception(message, throwable) {

    class BadRequest(message: String? = null, throwable: Throwable? = null) :
        NetworkException(message, throwable)

    class NotFound(message: String? = null, throwable: Throwable? = null) :
        NetworkException(message, throwable)

    class Forbidden(message: String? = null, throwable: Throwable? = null) :
        NetworkException(message, throwable)

    class InternalError(message: String? = null, throwable: Throwable? = null) :
        NetworkException(message, throwable)

    companion object{
        fun parse(statusCode: Int, message: String? = null, throwable: Throwable? = null) =
            when(statusCode){
                HttpURLConnection.HTTP_BAD_REQUEST -> BadRequest(
                    message, throwable
                )

                HttpURLConnection.HTTP_NOT_FOUND -> NotFound(
                    message, throwable
                )

                HttpURLConnection.HTTP_FORBIDDEN -> Forbidden(
                    message, throwable
                )

                else -> InternalError(
                    message, throwable
                )
            }
    }
}