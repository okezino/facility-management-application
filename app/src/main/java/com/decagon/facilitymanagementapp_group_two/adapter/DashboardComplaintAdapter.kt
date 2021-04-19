package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.DashboardRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.Complain
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints

class DashboardComplaintAdapter(private val clickListner: ComplaintClickListener) : RecyclerView.Adapter<DashboardComplaintAdapter.ViewHolder>() {
//    var firstComplain = Complain("AC is NOT working", "An suas viderer pro. Vis cu magna altera, ex his vivendo atomorum.", "Today")
//    var secComplain = Complain("Beans tastes bad", "Reprehenderit mollit excepteur labore deserunt officia laboris eiusmod cillum eu duis", "Yesterday")
//    var thirdComplain = Complain("Can’t wash my legs", "Aliqua mollit nisi incididunt id eu consequat eu cupidatat.", "20/12/2019")
//    var forthComplain = Complain("Leaking Tap", "Voluptate irure aliquip consectetur commodo ex ex.", "18/12/2019")
//    var fifthComplain = Complain("Can’t access HMO", "Ex Lorem veniam veniam irure sunt adipisicing culpa.", "17/12/2019")

 //   private var listOfComplains: MutableList<Complain> = mutableListOf(firstComplain, secComplain, thirdComplain, forthComplain, fifthComplain)

    private var listOfComplains = mutableListOf<Complaints>()

    inner class ViewHolder(private val binding: DashboardRecyclerViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Complaints) {
            binding.complainTitle.text = item.subject
            binding.complainDate.text = "Today"
            binding.complainDetails.text = item.description
            binding.complainLayout.setOnClickListener {
                clickListner.onCompalinClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DashboardRecyclerViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfComplains.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfComplains[position])
    }

//    fun updateDashboardComplainList(complain: Complain) {
//        listOfComplains.add(0, complain)
//        notifyItemInserted(0)
//    }

    fun loadData(complaints: List<Complaints>) {
        this.listOfComplains = complaints as MutableList<Complaints>
        notifyDataSetChanged()
    }
}
