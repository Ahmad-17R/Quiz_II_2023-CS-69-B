package com.example.application_with_api

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Complaint(
    var id: String = "",
    var studentName: String = "",
    var rollNumber: String = "",
    var title: String = "",
    var category: String = "",
    var priority: String = "",
    var description: String = "",
    var status: String = "Pending",
    var timestamp: Long = 0L
) : Serializable
