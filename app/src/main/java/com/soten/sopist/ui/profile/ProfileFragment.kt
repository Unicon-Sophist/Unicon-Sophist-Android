package com.soten.sopist.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.soten.sopist.R
import com.soten.sopist.databinding.FragmentProfileBinding
import com.soten.sopist.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentProfileBinding.inflate(layoutInflater).apply {
        lifecycleOwner = this@ProfileFragment
        viewModel = profileViewModel
    }

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginText.setOnClickListener {
            findNavController().navigate(R.id.navigationLogin)
        }

        binding.joinText.setOnClickListener {
            findNavController().navigate(R.id.navigationJoin)
        }

    }

    override fun observeData() {
        profileViewModel.userStatusLiveData.observe(viewLifecycleOwner) { state ->
            if (state == UserState.NORMAL) {
                binding.nonExistentUserGroup.visibility = View.VISIBLE
                binding.existentUserGroup.visibility = View.GONE
            }
            if (state == UserState.LOGIN) {
                binding.nonExistentUserGroup.visibility = View.GONE
                binding.existentUserGroup.visibility = View.VISIBLE
            }
        }
    }

}