package bssm2024.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "short_url")
data class ShortenUrl (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val num: Long = 0,

    val originalUrl: String,
    var encodedUrl: String,
)