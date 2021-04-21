package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.DashboardRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

class MyRequestAdapter : PagingDataAdapter<Complaints, MyRequestAdapter.MyRequestViewHolder>(REQUEST_COMPARATOR) {

    class MyRequestViewHolder(private val binding: DashboardRecyclerViewLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Complaints) {
            binding.complainTitle.text = item.subject
            binding.complainDate.text = "Today"
            binding.complainDetails.text = item.description
//            binding.complainLayout.setOnClickListener {
//                clickListner.onCompalinClicked()
//            }
        }
    }

    companion object {
        private val REQUEST_COMPARATOR = object : DiffUtil.ItemCallback<Complaints>() {
            override fun areItemsTheSame(oldItem: Complaints, newItem: Complaints): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Complaints, newItem: Complaints): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MyRequestViewHolder, position: Int) {
        val complain = getItem(position)
        if (complain != null) {
            holder.bind(complain)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestViewHolder {
        val binding = DashboardRecyclerViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyRequestViewHolder(binding)
    }
}