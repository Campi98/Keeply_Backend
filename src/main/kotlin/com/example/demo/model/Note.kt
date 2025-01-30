package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Entity
import jakarta.persistence.GenerationType
import jakarta.persistence.Column

@Entity
data class Note(
    @Id @GeneratedValue
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val photoUri: String? = null,
    @Column(columnDefinition = "TEXT")
    val photoBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)