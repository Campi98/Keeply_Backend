package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity
@Table(name = "users")
data class User(
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Int = 0,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column(nullable = false, unique = true)
    val email: String = "",
    
    @Column(nullable = false)
    val password: String = ""
)