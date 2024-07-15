package bssm2024.demo.service

import bssm2024.demo.model.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UrlRepository: JpaRepository<Url, Long> {
    fun findByEncodedUrl(encoded: String): Url
}