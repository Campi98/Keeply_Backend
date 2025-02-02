package com.example.demo.service

import org.springframework.stereotype.Service
import com.example.demo.repository.NoteRepository
import com.example.demo.model.Note

@Service
class NoteService(private val noteRepository: NoteRepository) {
    fun getDeletedNoteIds(userId: Long): List<Long> =
        noteRepository.findByUserIdAndIsDeletedTrue(userId).map { it.id }

    fun markAsDeleted(noteId: Long) {
        val note = noteRepository.findById(noteId).orElseThrow()
        noteRepository.save(note.copy(isDeleted = true))
    }
}