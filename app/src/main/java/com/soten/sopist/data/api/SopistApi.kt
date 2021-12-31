package com.soten.sopist.data.api

import com.soten.sopist.data.api.request.JoinRequest
import com.soten.sopist.data.api.request.LoginRequest
import com.soten.sopist.data.api.request.OAuthTokenKakao
import com.soten.sopist.data.api.response.SopistResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SopistApi {

    @POST("login")
    suspend fun loginRequest(@Body loginRequest: LoginRequest): Response<SopistResponse<String>>

    @POST("join")
    suspend fun joinRequest(@Body joinRequest: JoinRequest): Response<SopistResponse<String>>

    @POST("social/login/kakao")

    suspend fun kakaoLogin(@Body oAuthTokenKakao: OAuthTokenKakao): Response<SopistResponse<String>>

}