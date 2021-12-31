package com.soten.sopist.ui.adapter.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.soten.sopist.databinding.ItemMeetBinding


sealed class SopistViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    class MeetViewHolder(
        private val binding: ItemMeetBinding,
    ) : SopistViewHolder(binding) {

        fun bind() {

            binding.executePendingBindings()
        }
    }


//    class ExpertListViewHolder(
//        private val binding: ItemExpertListBinding,
//    ) : SopistViewHolder(binding) {
//
//        fun bind(homeItem: HomeItem.ItemExpertList) {
//            binding.rvExpert.apply {
//                adapter = ExpertAdapter(homeItem.expertList)
//            }
//            binding.executePendingBindings()
//        }
//    }

}