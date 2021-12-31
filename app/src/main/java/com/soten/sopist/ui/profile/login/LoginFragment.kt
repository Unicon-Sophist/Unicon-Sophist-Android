package com.soten.sopist.ui.profile.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.soten.sopist.R
import com.soten.sopist.data.api.request.OAuthTokenKakao
import com.soten.sopist.databinding.FragmentLoginBinding
import com.soten.sopist.ui.base.BaseFragment
import com.soten.sopist.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentLoginBinding.inflate(layoutInflater)

    private val loginViewModel by activityViewModels<ProfileViewModel>()

    private val mOAuthLoginInstance by lazy {
        OAuthLogin.getInstance().also { authModuel ->
            authModuel.init(
                context,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name))
        }
    }
    private val mContext by lazy { context }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this@LoginFragment
        binding.viewModel = loginViewModel

        binding.kakoLoginButton.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("Sopist", "로그인 실패", error)
                } else if (token != null) {
                    val oAuthToken = OAuthTokenKakao(
                        token.accessToken,
                        token.refreshToken,
                        token.accessTokenExpiresAt.toString(),
                        token.refreshTokenExpiresAt.toString()
                    )

                    loginViewModel.kakaoLogin(oAuthToken)

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("Sopist", "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i("Sopist", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        }
                    }

                    findNavController().navigateUp()
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }

        binding.naverLoginButton.setOnClickListener {
            mOAuthLoginInstance.startOauthLoginActivity(requireActivity(), mOAuthLoginHandler)
        }
    }

    @SuppressLint("HandlerLeak")
    private val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken = mOAuthLoginInstance.getAccessToken(mContext)
                val refreshToken = mOAuthLoginInstance.getRefreshToken(mContext)

                Log.d("Sopist", "accessToken : $accessToken \n" +
                        "refreshToken : $refreshToken")

                loginViewModel.setLoginState()
                findNavController().popBackStack()
            } else {
                val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                val errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext)
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
