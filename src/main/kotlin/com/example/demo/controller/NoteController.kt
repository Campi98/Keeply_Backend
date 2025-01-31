package com.example.demo.controller

import com.example.demo.model.Note
import com.example.demo.repository.NoteRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import com.example.demo.repository.UserRepository

@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteRepository: NoteRepository, private val userRepository: UserRepository) {

    @GetMapping
    fun getAllNotes(): List<Note> = 
        try {
            noteRepository.findAll().also { notes ->
                println("Found ${notes.size} notes")
            }
        } catch (e: Exception) {
            println("Error fetching notes: ${e.message}")
            e.printStackTrace()
            emptyList()
        }

    @PostMapping
    fun createNote(@RequestBody note: Note): Note {
        if (userRepository.existsById(note.userId)) {
            return noteRepository.save(note)
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

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
}