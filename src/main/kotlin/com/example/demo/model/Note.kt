package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Note(
    @Id @GeneratedValue
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val photoUri: String? = null,
    val photoBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)