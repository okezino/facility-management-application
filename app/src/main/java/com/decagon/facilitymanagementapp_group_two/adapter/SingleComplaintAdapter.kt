package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.data.Comment
import com.decagon.facilitymanagementapp_group_two.databinding.SingleComplaintItemBinding

class SingleComplaintAdapter : RecyclerView.Adapter<SingleComplaintAdapter.ViewHolder>() {
    // List of items for the placeholder data
    private var items = mutableListOf<Comment>()

    inner class ViewHolder(private val binding: SingleComplaintItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.fragmentSingleComplaintCommenterNameTv.text = item.text
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleComplaintItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setupItems(items: List<Comment>) {
        this.items = items as MutableList<Comment>
    }
}
