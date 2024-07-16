package bssm2024.demo.dto

import java.time.LocalDateTime

data class CreateUrlRes (
    val original_url: String,
    val encoded_url: String,
    val created_at: LocalDateTime
)