package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository) {

    @GetMapping
    fun getAllUsers(): List<User> = 
        userRepository.findAll()

    @PostMapping
    fun createUser(@RequestBody user: User): User = 
        userRepository.save(user)

    @PostMapping("/login")
    fun login(@RequestBody credentials: Map<String, String>): User {
        val email = credentials["email"] ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        val password = credentials["password"] ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        
        val userToReturn = userRepository.findByEmailAndPassword(email, password)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

        userRepository.save(userToReturn.copy(loggedIn = true))

        return userToReturn
    }

    @PostMapping("/logout")
    fun logout(@RequestBody credentials: Map<String, String>): User {
        val email = credentials["email"] ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        val password = credentials["password"] ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        
        val userToReturn = userRepository.findByEmailAndPassword(email, password)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

        userRepository.save(userToReturn.copy(loggedIn = false))

        return userToReturn
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody user: User): User =
        userRepository.findById(id).map { existingUser ->
            userRepository.save(user.copy(userId = existingUser.userId))
        }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @DeleteMapping("/{id}") 
    fun deleteUser(@PathVariable id: Int) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}