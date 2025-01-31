package com.example.demo.controller

import com.example.demo.model.Note
import com.example.demo.repository.NoteRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteRepository: NoteRepository) {

    @GetMapping
    fun getAllNotes(): List<Note> = 
        noteRepository.findAll()

    @PostMapping
    fun createNote(@RequestBody note: Note): Note =
        noteRepository.save(note)

    @GetMapping("/user/{userId}")
    fun getNotesByUser(@PathVariable userId: Int): List<Note> =
        noteRepository.findByUser_UserId(userId)

    @PutMapping("/{id}")  
    fun updateNote(@PathVariable id: Long, @RequestBody note: Note): Note =
        noteRepository.findById(id).map { existingNote ->
            noteRepository.save(note.copy(id = existingNote.id))
        }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Long) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
    
    // delete all notes
    @DeleteMapping
    fun deleteAllNotes() {
        noteRepository.deleteAll()
    }

    // Add endpoint to get notes by user
    @GetMapping("/user/{userId}")
    fun getNotesByUser(@PathVariable userId: Int): List<Note> =
        try {
            noteRepository.findByUser_UserId(userId)  // Changed from findByUserUserId to findByUser_UserId
        } catch (e: Exception) {
            logger.error("Error fetching notes for user $userId", e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
}