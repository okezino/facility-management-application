package com.decagon.facilitymanagementapp_group_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.facilitymanagementapp_group_two.databinding.DashboardRecyclerViewLayoutBinding
import com.decagon.facilitymanagementapp_group_two.model.data.Complain
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Complaints
import com.decagon.facilitymanagementapp_group_two.model.data.entities.Request

class DashboardComplaintAdapter(private val clickListner: ComplaintClickListener) : RecyclerView.Adapter<DashboardComplaintAdapter.ViewHolder>() {

    private var listOfRequests = mutableListOf<Request>()

    inner class ViewHolder(private val binding: DashboardRecyclerViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Request) {
            binding.complainTitle.text = item.title
            binding.complainDate.text = "Today"
            binding.complainDetails.text = item.question
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
        return listOfRequests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfRequests[position])
    }

    fun loadData(complaints: List<Request>) {
        this.listOfRequests = complaints as MutableList<Request>
        notifyDataSetChanged()
    }

    fun getComplaintId(id:Int) : String?{
        return listOfRequests[id].id
    }

    fun getComplain(id : Int) : Request{
        return listOfRequests[id]
    }
}
