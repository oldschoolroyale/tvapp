package com.brmlab.shophelp.utils

data class BaseResponse <T> (val status: Int, val data: T?, val error: String? = null)

enum class Status{
    LOADING, SUCCESS, ERROR
}

data class BaseModel <T>(val status : Status, val response: BaseResponse<T>?)