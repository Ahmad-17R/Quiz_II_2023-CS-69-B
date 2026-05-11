package com.example.application_with_api

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ComplaintDetailActivity : AppCompatActivity() {

    private lateinit var tvDetailTitle: TextView
    private lateinit var tvDetailStatus: TextView
    private lateinit var tvDetailStudentName: TextView
    private lateinit var tvDetailRollNumber: TextView
    private lateinit var tvDetailCategory: TextView
    private lateinit var tvDetailPriority: TextView
    private lateinit var tvDetailDate: TextView
    private lateinit var tvDetailDescription: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_detail)

        tvDetailTitle = findViewById(R.id.tvDetailTitle)
        tvDetailStatus = findViewById(R.id.tvDetailStatus)
        tvDetailStudentName = findViewById(R.id.tvDetailStudentName)
        tvDetailRollNumber = findViewById(R.id.tvDetailRollNumber)
        tvDetailCategory = findViewById(R.id.tvDetailCategory)
        tvDetailPriority = findViewById(R.id.tvDetailPriority)
        tvDetailDate = findViewById(R.id.tvDetailDate)
        tvDetailDescription = findViewById(R.id.tvDetailDescription)
        btnBack = findViewById(R.id.btnBack)

        val complaint = intent.getSerializableExtra("complaint_data") as? Complaint

        complaint?.let {
            tvDetailTitle.text = it.title
            tvDetailStatus.text = "Status: ${it.status}"
            tvDetailStudentName.text = "Student Name: ${it.studentName}"
            tvDetailRollNumber.text = "Roll Number: ${it.rollNumber}"
            tvDetailCategory.text = "Category: ${it.category}"
            tvDetailPriority.text = "Priority: ${it.priority}"
            tvDetailDescription.text = it.description

            val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            val dateStr = it.timestamp?.let { date -> dateFormat.format(date) } ?: "N/A"
            tvDetailDate.text = "Submitted on: $dateStr"
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
