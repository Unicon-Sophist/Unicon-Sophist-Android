package com.soten.sopist.data.api.request

data class JoinRequest(
    val memId: String,
    val memPw: String,
    val memNickname: String,
    val memGender: String,
    val memContents: String
)