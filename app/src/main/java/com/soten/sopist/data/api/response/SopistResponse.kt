package com.soten.sopist.data.api.response

data class SopistResponse<T>(
    val status: Int,
    val result: T
)