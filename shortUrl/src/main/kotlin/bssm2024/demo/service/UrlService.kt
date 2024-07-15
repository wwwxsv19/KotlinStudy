package bssm2024.demo.service

import bssm2024.demo.dto.CreateUrlRes
import bssm2024.demo.model.ShortenUrl
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UrlService(
    val shortUrlRepository: ShortUrlRepository,
    val base: String = "http://localhost:8082/short-url/"
) {
    fun create(originalUrl: String): CreateUrlRes {
        val encodedUrl = originalUrl.hashCode().toString(radix = 26)

        val url: ShortenUrl = shortUrlRepository.save(
            ShortenUrl(
                originalUrl = originalUrl,
                encodedUrl = base + encodedUrl
            )
        )

        return CreateUrlRes(
            original_url = url.originalUrl,
            encoded_url = url.encodedUrl
        )
    }

    fun readAll(): List<ShortenUrl>
        = shortUrlRepository.findAll()

    fun redirect(encoded: String): ShortenUrl?
        = shortUrlRepository.findByEncodedUrl(base + encoded).getOrNull()
}