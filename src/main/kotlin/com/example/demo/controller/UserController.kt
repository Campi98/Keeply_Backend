package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

data class LoginRequest(val email: String, val password: String)

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: LoginRequest): User {
        return userRepository.findByEmailAndPassword(credentials.email, credentials.password)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }
}