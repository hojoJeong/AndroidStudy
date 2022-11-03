package com.ssafy.template.src.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.ssafy.template.R
import com.ssafy.template.config.BaseFragment
import com.ssafy.template.databinding.FragmentHomeBinding
import com.ssafy.template.src.main.home.models.PostSignUpRequest
import com.ssafy.template.src.main.home.models.SignUpResponse
import com.ssafy.template.src.main.home.models.UserResponse

private const val TAG = "HomeFragment_싸피"
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
        HomeFragmentView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeButtonTryGetJwt.setOnClickListener {
            showLoadingDialog(requireContext())
            HomeService(this).tryGetUsers()
        }

        binding.homeBtnTryPostHttpMethod.setOnClickListener {
            val userId = binding.homeEtId.text.toString()
            val password = binding.homeEtPw.text.toString()
            val postRequest = PostSignUpRequest(userId = userId, password = password)
            showLoadingDialog(requireContext())
            HomeService(this).tryPostSignUp(postRequest)
        }
    }

    override fun onGetUserSuccess(response: UserResponse) {
        dismissLoadingDialog()
        for (user in response.result) {
            Log.d("HomeFragment", user.toString())
        }
        binding.homeButtonTryGetJwt.text = response.message
        showCustomToast("${response.result}")
    }

    override fun onGetUserFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostSignUpSuccess(response: SignUpResponse) {
        dismissLoadingDialog()
        binding.homeBtnTryPostHttpMethod.text = response.message
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostSignUpFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}