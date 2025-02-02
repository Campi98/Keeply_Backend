package com.example.demo.controller

import com.example.demo.model.Note
import com.example.demo.repository.NoteRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import com.example.demo.repository.UserRepository
import com.example.demo.service.NoteService

@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteRepository: NoteRepository, private val userRepository: UserRepository, private val noteService: NoteService) {

    @GetMapping
    fun getUserNotes(@RequestParam("userId") userId: Int): List<Note> =
        try {
            noteRepository.findAll().filter { it.userId == userId }
        } catch (e: Exception) {
            println("Error fetching notes: ${e.message}")
            e.printStackTrace()
            emptyList()
        }

    @PostMapping
    fun createNote(@RequestBody note: Note): Note {
        if (note.title.isEmpty() || note.content.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }

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

    //Delete all notes by userId
    @DeleteMapping()
    fun deleteAllNotesByUserId(@RequestParam("userId") userId: Int) {
        noteRepository.deleteByUserId(userId)
    }

    @GetMapping("/deleted")
    fun getDeletedNotes(@RequestParam userId: Long): List<Long> =
        noteService.getDeletedNoteIds(userId)

    @PostMapping("/deleted")
    fun markAsDeleted(@RequestParam noteId: Long) =
        noteService.markAsDeleted(noteId)
}