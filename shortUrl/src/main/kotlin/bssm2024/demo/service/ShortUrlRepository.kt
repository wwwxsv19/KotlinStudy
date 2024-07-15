package bssm2024.demo.service

import bssm2024.demo.model.ShortenUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ShortUrlRepository: JpaRepository<ShortenUrl, Long> {
    fun findByEncodedUrl(encoded: String): Optional<ShortenUrl>
}