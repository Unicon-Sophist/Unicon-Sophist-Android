package com.soten.sopist.ui.category

import com.soten.sopist.databinding.FragmentCategoryBinding
import com.soten.sopist.ui.base.BaseFragment

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentCategoryBinding.inflate(layoutInflater)

}