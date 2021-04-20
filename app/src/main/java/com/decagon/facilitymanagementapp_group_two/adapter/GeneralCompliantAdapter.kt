package com.decagon.facilitymanagementapp_group_two.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.FeedsRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.utils.loadImage

class GeneralCompliantAdapter : RecyclerView.Adapter<GeneralCompliantAdapter.ViewHolder>() {
    private var data = mutableListOf<Complaints>()

    class ViewHolder(private val binding: FeedsRecyclerViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Complaints) {
                val user = "${item.userFirstName} ${item.userLastName}"
                binding.complaintName.text = user
                binding.complainDetails.text = item.description
                binding.complainDate.text = "Today"
                item.userImgUrl?.let {
                    binding.profileImage.loadImage(it)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = FeedsRecyclerViewLayoutBinding.inflate(LayoutInflater
            .from(parent.context), parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun loadData(complaints: List<Complaints>) {
        this.data = complaints as MutableList<Complaints>
        this.notifyDataSetChanged()
    }
}