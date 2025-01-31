package com.example.demo.repository

import com.example.demo.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    fun findByUser_UserId(userId: Int): List<Note>
}