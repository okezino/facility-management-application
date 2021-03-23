package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.R
import com.decagon.facilitymanagementapp_group_two.data.SingleComplaint
import com.decagon.facilitymanagementapp_group_two.databinding.FeedsRecyclerViewLayoutBinding


class FoodComplainAdapter : RecyclerView.Adapter<FoodComplainAdapter.ViewHolder>() {
    var firstComplain = SingleComplaint(R.drawable.fin,"Alhaji Okezi","An suas viderer pro. Vis cu magna altera, ex his vivendo atomorum.","Today")
    var secComplain = SingleComplaint(R.drawable.fin3,"Obehi Goodday","Reprehenderit mollit excepteur labore deserunt officia laboris eiusmod cillum eu duis","Yesterday")


    var listOfComplains = mutableListOf<SingleComplaint>(firstComplain,secComplain)

    class ViewHolder(private val binding: FeedsRecyclerViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : SingleComplaint){
            binding.complaintName.text = item.complainName
            binding.complainDetails.text = item.complaindetail
            binding.complainDate.text = item.compalindate
            binding.profileImage.setImageResource(item.userImage)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodComplainAdapter.ViewHolder {
        val binding = FeedsRecyclerViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  listOfComplains.size
    }

    override fun onBindViewHolder(holder: FoodComplainAdapter.ViewHolder, position: Int) {
        holder.bind(listOfComplains[position])
    }

    fun updateFoodComplainList(complain : SingleComplaint){
        listOfComplains.add(0,complain)
        notifyItemInserted(0)
    }
}