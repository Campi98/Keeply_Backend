package com.example.demo.repository

import jakarta.persistence.EntityManager
import com.example.demo.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface CustomNoteRepository {
    fun deleteByUserId(userId: Int)
    fun findByUserIdAndIsDeletedTrue(userId: Long): List<Note>
}

@Repository
class CustomNoteRepositoryImpl(
    private val entityManager: EntityManager
) : CustomNoteRepository {
    
    @Transactional
    override fun deleteByUserId(userId: Int) {
        val query = entityManager.createQuery("DELETE FROM Note n WHERE n.userId = :userId")
        query.setParameter("userId", userId)
        query.executeUpdate()
    }

    override fun findByUserIdAndIsDeletedTrue(userId: Long): List<Note> {
        val query = entityManager.createQuery(
            "SELECT n FROM Note n WHERE n.userId = :userId AND n.isDeleted = true", 
            Note::class.java
        )
        query.setParameter("userId", userId)
        return query.resultList
    }
}

@Repository
interface NoteRepository : JpaRepository<Note, Long>, CustomNoteRepository {
}