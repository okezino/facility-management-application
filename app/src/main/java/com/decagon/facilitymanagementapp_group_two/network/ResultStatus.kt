package com.decagon.facilitymanagementapp_group_two.network

/**
 * A sealed representing states and results of a network call
 */
sealed class ResultStatus<out T> {

    data class Success<out T>(val value: T) : ResultStatus<T>()

    data class GenericError(
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : ResultStatus<Nothing>()

    object NetworkError : ResultStatus<Nothing>()
}
