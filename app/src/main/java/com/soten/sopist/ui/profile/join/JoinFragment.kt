package com.soten.sopist.ui.profile.join

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.soten.sopist.databinding.FragmentJoinBinding
import com.soten.sopist.ui.base.BaseFragment
import com.soten.sopist.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>() {

    override var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentJoinBinding.inflate(layoutInflater).apply {
        lifecycleOwner = this@JoinFragment
        viewModel = joinViewModel
    }

    private val joinViewModel by activityViewModels<ProfileViewModel>()

}