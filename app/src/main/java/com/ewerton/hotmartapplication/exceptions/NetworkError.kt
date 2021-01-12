package com.ewerton.hotmartapplication.exceptions

import retrofit2.HttpException
import java.net.ConnectException

object NetworkError {

    private const val CONNECTION_ERROR = "CONNECTION_ERROR"
    private const val SERVER_ERROR = "SERVER_ERROR"
    private const val HTTP_EXCEPTION = "HTTP_EXCEPTION"

    fun getExceptionType(e: Exception): String {
        return when (e) {
            is ConnectException -> {
                CONNECTION_ERROR
            }
            is HttpException -> {
                HTTP_EXCEPTION
            }
            else -> {
                SERVER_ERROR
            }
        }
    }


}