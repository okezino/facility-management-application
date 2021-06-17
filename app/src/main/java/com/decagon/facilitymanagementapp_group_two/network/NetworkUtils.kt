package com.decagon.facilitymanagementapp_group_two.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * An abstraction of try and catch block in a coroutine scope providing a clean
 * and safe API for making network calls
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultStatus.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultStatus.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultStatus.GenericError(code, errorResponse)
                }
                else -> ResultStatus.GenericError()
            }
        }
    }
}

/**
 * Function for convert/deserialize HttpException to Kotlin class using Moshi library
 */
private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory())
                .build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (e: Exception) { null }
}
