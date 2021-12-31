package com.soten.sopist.ui.meet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.soten.sopist.databinding.FragmentMeetBinding
import com.soten.sopist.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetFragment : BaseFragment<FragmentMeetBinding>() {

    override var _binding: FragmentMeetBinding? = null
    private val binding get() = _binding!!
    override fun getDataBinding() = FragmentMeetBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.thumbnail1.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("소피스트")
                .setMessage("화상통화방에 참여하시겠습니까?")
                .setNegativeButton("No") { _, _ -> }
                .setPositiveButton("Yes") { _, _ -> }
                .create()
                .show()
        }
    }

}