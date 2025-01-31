package com.example.demo.controller

import com.example.demo.model.Note
import com.example.demo.repository.NoteRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteRepository: NoteRepository) {

    @GetMapping
    fun getAllNotes(): List<Note> = 
        try {
            val notes = noteRepository.findAll()
            if (notes.isEmpty()) {
                println("No notes found in database")
            } else {
                println("Found ${notes.size} notes")
            }
            notes
        } catch (e: Exception) {
            println("Error fetching notes: ${e.message}")
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching notes")
        }

    @PostMapping
    fun createNote(@RequestBody note: Note): Note = 
        noteRepository.save(note)

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
            noteRepository.findByUserUserId(userId)
        } catch (e: Exception) {
            println("Error fetching notes for user $userId: ${e.message}")
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
}