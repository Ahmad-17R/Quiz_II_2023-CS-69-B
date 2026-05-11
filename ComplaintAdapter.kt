package com.example.application_with_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(
    private var complaints: List<Complaint>,
    private val onItemClick: (Complaint) -> Unit
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    class ComplaintViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvStudentInfo: TextView = view.findViewById(R.id.tvStudentInfo)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvPriority: TextView = view.findViewById(R.id.tvPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = complaints[position]
        holder.tvTitle.text = complaint.title
        holder.tvStudentInfo.text = "${complaint.studentName} - ${complaint.rollNumber}"
        holder.tvCategory.text = "Category: ${complaint.category}"
        holder.tvPriority.text = "Priority: ${complaint.priority}"

        holder.itemView.setOnClickListener { onItemClick(complaint) }
    }

    override fun getItemCount() = complaints.size

    fun updateList(newList: List<Complaint>) {
        complaints = newList
        notifyDataSetChanged()
    }
}
