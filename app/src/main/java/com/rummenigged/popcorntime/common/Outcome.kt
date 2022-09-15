package com.rummenigged.popcorntime.common

sealed class Outcome<out T> {
    data class Success<out T>(val value: T): Outcome<T>()
    data class Failure(
        val statusCode: Int,
        val errorResponse: BaseErrorResponse? = null
    ): Outcome<Nothing>()
}