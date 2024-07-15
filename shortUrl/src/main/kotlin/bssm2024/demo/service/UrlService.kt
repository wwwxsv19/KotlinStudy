package bssm2024.demo.service

import bssm2024.demo.model.Url
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UrlService(
    val urlRepository: UrlRepository,
    val base: String = "http://localhost:8082/api/short-url/"
) {
    fun save(url: Url): Url {
        return urlRepository.save(url)
    }

    fun create(originalUrl: String): String {
        return "http://localhost:8082/short-url/" + originalUrl.hashCode().toString(radix = 26)
    }

    fun readAll(): List<Url>
        = urlRepository.findAll()

    fun redirect(encoded: String): Url {
        val url = urlRepository.findByEncodedUrl(base + encoded)
        url.clickCount ++
        urlRepository.save(url)
        return url
    }
}