package com.soten.sopist.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB: ViewDataBinding> : Fragment() {

    abstract var _binding: DB?

    abstract fun getDataBinding(): DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getDataBinding()

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        observeData()
    }

    open fun bindViews() = Unit

    open fun initViews() = Unit

    open fun observeData() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}