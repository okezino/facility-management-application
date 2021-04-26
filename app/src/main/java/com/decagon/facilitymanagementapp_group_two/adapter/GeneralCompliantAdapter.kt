package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.FeedsRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request
import com.decagon.facilitymanagementapp_group_two.utils.loadImage

class GeneralCompliantAdapter(private val clickListener: ComplaintClickListener) : PagingDataAdapter<Complaints, GeneralCompliantAdapter.ViewHolder>(
    COMPLAINS_COMPARATOR){

    inner class ViewHolder(private val binding: FeedsRecyclerViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Complaints) {
            val user = "${item.userFirstName} ${item.userLastName}"
            binding.complaintName.text = user
            binding.complainDetails.text = item.description
            binding.complainDate.text = "Today"
            item.userImgUrl?.let {
                binding.profileImage.loadImage(it)
            }
            binding.feedRecyclerLayout.setOnClickListener{
                clickListener.onCompalinClicked(item.subject,item.description,item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = FeedsRecyclerViewLayoutBinding.inflate(
            LayoutInflater
                .from(parent.context),
            parent, false
        )
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val complains = getItem(position)
        if (complains != null) {
            holder.bind(complains)
        }
    }

    companion object {
        val COMPLAINS_COMPARATOR = object : DiffUtil.ItemCallback<Complaints>() {
            override fun areItemsTheSame(oldItem: Complaints, newItem: Complaints): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Complaints, newItem: Complaints): Boolean =
                oldItem == newItem

        }
    }
}

