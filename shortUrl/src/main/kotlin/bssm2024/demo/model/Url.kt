package bssm2024.demo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "short_url")
data class Url (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val num: Long = 0,

    val originalUrl: String,

    var encodedUrl: String,

    var clickCount: Long,

    val createdAt: LocalDateTime = LocalDateTime.now()
)