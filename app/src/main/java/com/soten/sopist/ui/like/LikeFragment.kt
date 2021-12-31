package com.soten.sopist.ui.like

import com.soten.sopist.databinding.FragmentLikeBinding
import com.soten.sopist.ui.base.BaseFragment

class LikeFragment : BaseFragment<FragmentLikeBinding>() {

    override var _binding: FragmentLikeBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentLikeBinding.inflate(layoutInflater)

}