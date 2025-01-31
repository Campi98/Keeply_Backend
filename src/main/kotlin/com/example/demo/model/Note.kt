package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "notes")
data class Note(
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    val user: User,
    
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