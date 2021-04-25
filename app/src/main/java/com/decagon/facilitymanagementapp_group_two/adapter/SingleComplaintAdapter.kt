package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.SingleComplaintItemBinding
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Comment
import com.decagon.facilitymanagementapp_group_two.utils.loadImage

class SingleComplaintAdapter(private val items : List<Comment>, private val clickListner: ComplaintClickListener) : RecyclerView.Adapter<SingleComplaintAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SingleComplaintItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            val fullName = "${item.user.firstName} ${item.user.lastName}"
            binding.fragmentSingleComplaintCommenterNameTv.text = fullName
            binding.fragmentSingleComplaintCommentBodyTv.text = item.comment
            item.user.avatarUrl?.let { binding.cardView2.loadImage(it)}
            binding.fragmentSingleComplaintTimeElapsedTv.text = "2 hours ago"
            binding.fragmentSingleComplaintReplyTv.setOnClickListener {
                clickListner.onCompalinClicked()
            }

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

}
