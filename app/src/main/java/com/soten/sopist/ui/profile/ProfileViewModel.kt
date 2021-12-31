package com.soten.sopist.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soten.sopist.data.api.SopistApi
import com.soten.sopist.data.api.request.JoinRequest
import com.soten.sopist.data.api.request.LoginRequest
import com.soten.sopist.data.api.request.OAuthTokenKakao
import com.soten.sopist.data.db.preference.SharedPreferenceManager
import com.soten.sopist.data.db.preference.SharedPreferenceManager.Companion.KEY_DEFAULT
import com.soten.sopist.data.db.preference.SharedPreferenceManager.Companion.KEY_LOGIN
import com.soten.sopist.data.db.preference.SharedPreferenceManager.Companion.KEY_USER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sopistApi: SopistApi,
    private val sharedPreferenceManager: SharedPreferenceManager,
) : ViewModel() {

    private val _userStatusLiveData = MutableLiveData<UserState>()
    val userStatusLiveData: LiveData<UserState> get() = _userStatusLiveData

    private val _userNameLiveData = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userNameLiveData

    init {
        if (sharedPreferenceManager.getBoolean(KEY_LOGIN)) {
            _userStatusLiveData.value = UserState.LOGIN
        } else {
            _userStatusLiveData.value = UserState.NORMAL
        }

        if (sharedPreferenceManager.getString(KEY_USER_NAME) != KEY_DEFAULT) {
            Log.d("Sopist", "name1 : ${sharedPreferenceManager.getString(KEY_USER_NAME)}")
            _userNameLiveData.value = sharedPreferenceManager.getString(KEY_USER_NAME)
        } else {
            Log.d("Sopist", "name2 : ${sharedPreferenceManager.getString(KEY_USER_NAME)}")
            _userNameLiveData.value = "로그인으로 \nSopist 200% 활용!"
        }
    }

    fun logOut() {
        _userNameLiveData.value = "로그인으로 \nSopist 200% 활용!"

        sharedPreferenceManager.putBoolean(KEY_LOGIN, false)
        _userStatusLiveData.value = UserState.NORMAL

        sharedPreferenceManager.putString(KEY_USER_NAME, KEY_DEFAULT)

        Log.d("Sopist", "name3 : ${sharedPreferenceManager.getString(KEY_USER_NAME)}")
    }

    // 로그인
    val loginId = MutableLiveData<String>()
    val loginPw = MutableLiveData<String>()

    fun loginRequest() = viewModelScope.launch {
        val memId = loginId.value ?: throw IllegalArgumentException("id를 입력해주세요.")
        val memPw = loginPw.value ?: throw IllegalArgumentException("pw를 입력해주세요.")

        try {
            sopistApi.loginRequest(LoginRequest(memId, memPw)).body()

            sharedPreferenceManager.putBoolean(KEY_LOGIN, true)
            _userStatusLiveData.value = UserState.LOGIN

        } catch (e: Exception) {
            Log.d("TestT", "실패", e)
        }
    }

    fun kakaoLogin(oAuthTokenKakao: OAuthTokenKakao) = viewModelScope.launch {
        try {
            val request = sopistApi.kakaoLogin(oAuthTokenKakao).body()

            Log.d("Sopist", "oAuthTokenKakao : $oAuthTokenKakao")

            sharedPreferenceManager.putBoolean(KEY_LOGIN, true)
            _userStatusLiveData.value = UserState.LOGIN
            if (request?.status == 200) {
                Log.d("Sopist", "성공")
            } else {
                Log.d("Sopist", "200을 받지 못함 ${request?.status}")
            }

        } catch (e: Exception) {
            Log.d("Sopist", "실패", e)
        }
    }

    fun setLoginState() {
        sharedPreferenceManager.putBoolean(KEY_LOGIN, true)
        _userStatusLiveData.value = UserState.LOGIN

        sharedPreferenceManager.putString(KEY_USER_NAME, "네이버 로그인 유저")
        _userNameLiveData.value = "네이버 로그인 유저"
    }

    // 회원가입
    val joinId = MutableLiveData<String>()
    val joinPw = MutableLiveData<String>()
    val joinNickname = MutableLiveData<String>()
    val joinGender = MutableLiveData<String>()

    fun joinRequest() = viewModelScope.launch {
        try {
            val memId = joinId.value ?: throw IllegalArgumentException("값이 없음")
            val memPw = joinPw.value ?: throw IllegalArgumentException("값이 없음")
            val memNickname = joinNickname.value ?: throw IllegalArgumentException("값이 없음")
            val memGender = joinGender.value ?: throw IllegalArgumentException("값이 없음")

            val joinRequest = JoinRequest(memId, memPw, memNickname, memGender, "안녕하세요")

            val result = sopistApi.joinRequest(joinRequest).body()
            if (result?.status == 200) {
                Log.d("Sopist", "성공 : $result")
            } else {
                Log.d("Sopist", "실패 : $result")
            }
        } catch (e: Exception) {
            Log.d("Sopist", "에러 발생", e)
        }
    }

    fun setMale() {
        joinGender.value = "M"
    }

    fun setFemale() {
        joinGender.value = "F"
    }

    fun setEtc() {
        joinGender.value = "E"
    }

}