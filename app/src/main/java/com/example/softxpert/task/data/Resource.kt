package com.example.softxpert.task.data

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)

    class Loading<T> : Resource<T>()
}

suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

    return try {
        val response: Response<T> = apiToBeCalled()

        if (response.isSuccessful) {
            Resource.Success(data = response.body()!!)
        } else {
            Resource.Error(errorMessage = "Something went wrong")
        }

    } catch (e: HttpException) {
        Resource.Error(errorMessage = e.message ?: "Something went wrong")
    } catch (e: IOException) {
        Resource.Error("Please check your network connection")
    } catch (e: Exception) {
        Resource.Error(errorMessage = "Something went wrong")
    }
}
