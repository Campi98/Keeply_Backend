package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity
data class Note(
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long = 0,
    
    @Column(nullable = false)
    val title: String = "",
    
    @Column(nullable = false)
    val content: String = "",
    
    @Column
    val photoUri: String? = null,
    
    @Column(columnDefinition = "TEXT")
    val photoBase64: String? = null,
    
    @Column(nullable = false)
    val timestamp: Long = System.currentTimeMillis(),
    
    @Column(nullable = false)
    val synced: Boolean = false
)