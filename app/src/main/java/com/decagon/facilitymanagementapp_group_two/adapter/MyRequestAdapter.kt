package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.DashboardRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

class MyRequestAdapter(private val clickListener: ComplaintClickListener) :
    PagingDataAdapter<Request, MyRequestAdapter.MyRequestViewHolder>(REQUEST_COMPARATOR) {

   inner class MyRequestViewHolder(private val binding: DashboardRecyclerViewLayoutBinding):
       RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Request) {
            binding.complainTitle.text = item.title
            binding.complainDate.text = "Today"
            binding.complainDetails.text = item.question
            binding.complainLayout.setOnClickListener {
                clickListener.onCompalinClicked(item.title, item.question,item.id)
            }
        }
    }

    companion object {
        private val REQUEST_COMPARATOR = object : DiffUtil.ItemCallback<Request>() {
            override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MyRequestViewHolder, position: Int) {
        val request = getItem(position)
        if (request != null)
            holder.bind(request)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestViewHolder {
        val binding = DashboardRecyclerViewLayoutBinding.inflate(LayoutInflater
            .from(parent.context), parent, false)
        return MyRequestViewHolder(binding)
    }

    fun getComplaintId(id: Int): String? {
        return getItem(id)!!.id
    }

    fun getComplain(id: Int): Request {
        return getItem(id)!!
    }

}