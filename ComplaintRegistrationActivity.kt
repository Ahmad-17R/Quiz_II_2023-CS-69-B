package com.example.application_with_api

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class ComplaintRegistrationActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var etRollNumber: EditText
    private lateinit var etComplaintTitle: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerPriority: Spinner
    private lateinit var etDescription: EditText
    private lateinit var btnSubmit: Button
    
    private val database = FirebaseDatabase.getInstance().getReference("complaints")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_registration)

        etStudentName = findViewById(R.id.etStudentName)
        etRollNumber = findViewById(R.id.etRollNumber)
        etComplaintTitle = findViewById(R.id.etComplaintTitle)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        etDescription = findViewById(R.id.etDescription)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            submitComplaint()
        }
    }

    private fun submitComplaint() {
        val name = etStudentName.text.toString().trim()
        val roll = etRollNumber.text.toString().trim()
        val title = etComplaintTitle.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val priority = spinnerPriority.selectedItem.toString()
        val desc = etDescription.text.toString().trim()

        if (name.isEmpty() || roll.isEmpty() || title.isEmpty() || desc.isEmpty() || 
            category == "Select Category" || priority == "Select Priority") {
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val complaintId = database.push().key ?: return
        
        val complaintMap = hashMapOf(
            "id" to complaintId,
            "studentName" to name,
            "rollNumber" to roll,
            "title" to title,
            "category" to category,
            "priority" to priority,
            "description" to desc,
            "status" to "Pending",
            "timestamp" to ServerValue.TIMESTAMP
        )

        btnSubmit.isEnabled = false
        btnSubmit.text = "Submitting..."

        database.child(complaintId).setValue(complaintMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Complaint Registered Successfully!", Toast.LENGTH_LONG).show()
                clearForm()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                btnSubmit.isEnabled = true
                btnSubmit.text = "Submit Complaint"
            }
    }

    private fun clearForm() {
        etStudentName.text.clear()
        etRollNumber.text.clear()
        etComplaintTitle.text.clear()
        etDescription.text.clear()
        spinnerCategory.setSelection(0)
        spinnerPriority.setSelection(0)
    }
}
