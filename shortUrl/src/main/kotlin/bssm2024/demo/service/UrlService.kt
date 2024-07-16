package bssm2024.demo.service

import bssm2024.demo.model.Url
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UrlService(
    val urlRepository: UrlRepository,
    val base: String = "http://localhost:8082/api/short-url/"
) {
    fun save(url: Url): Url {
        return urlRepository.save(url)
    }

    fun create(original: String): String {
        return "http://localhost:8082/api/short-url/" + original.hashCode().toString(radix = 26)
    }

    fun checkOriginal(original: String): Url? {
        return try {
            urlRepository.findByOriginalUrl(original)
        } catch(e: Exception) {
            return null
        }
    }

    fun readDefault(): List<Url> {
        return urlRepository.findByCreatedAtBetween(LocalDateTime.now().minusMinutes(10), LocalDateTime.now())
    }

    fun readAfterTime(time: LocalDateTime): List<Url> {
        return urlRepository.findByCreatedAtAfter(time)
    }

    fun redirect(encoded: String): Url {
        val url = urlRepository.findByEncodedUrl(base + encoded)
        url.clickCount ++
        urlRepository.save(url)
        return url
    }
}