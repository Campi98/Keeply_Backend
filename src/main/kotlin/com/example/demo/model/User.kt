package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class User(
    @Id @GeneratedValue
    val userId: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val type: String
)