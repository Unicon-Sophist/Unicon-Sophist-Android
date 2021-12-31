package com.soten.sopist.data.api.request

data class OAuthTokenKakao(
    val access_token: String,
    val refresh_token: String,
    val expires_in: String,
    val refresh_token_expires_in: String
)