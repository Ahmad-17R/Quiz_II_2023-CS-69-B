package com.example.application_with_api

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ComplaintListActivity : AppCompatActivity() {

    private lateinit var rvComplaints: RecyclerView
    private lateinit var tvEmptyMessage: TextView
    private lateinit var fabAddComplaint: FloatingActionButton
    private lateinit var adapter: ComplaintAdapter
    private val dbRef = FirebaseDatabase.getInstance().getReference("complaints")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_list)

        rvComplaints = findViewById(R.id.rvComplaints)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        fabAddComplaint = findViewById(R.id.fabAddComplaint)

        rvComplaints.layoutManager = LinearLayoutManager(this)
        adapter = ComplaintAdapter(emptyList()) { complaint ->
            val intent = Intent(this, ComplaintDetailActivity::class.java)
            intent.putExtra("complaint_data", complaint)
            startActivity(intent)
        }
        rvComplaints.adapter = adapter

        fabAddComplaint.setOnClickListener {
            startActivity(Intent(this, ComplaintRegistrationActivity::class.java))
        }

        fetchComplaints()
    }

    private fun fetchComplaints() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Complaint>()
                for (data in snapshot.children) {
                    val complaint = data.getValue(Complaint::class.java)
                    complaint?.let { list.add(it) }
                }
                
                // Sort by timestamp descending (latest first)
                val sortedList = list.sortedByDescending { it.timestamp }
                
                if (sortedList.isNotEmpty()) {
                    adapter.updateList(sortedList)
                    rvComplaints.visibility = View.VISIBLE
                    tvEmptyMessage.visibility = View.GONE
                } else {
                    rvComplaints.visibility = View.GONE
                    tvEmptyMessage.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ComplaintListActivity, "Fetch failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
