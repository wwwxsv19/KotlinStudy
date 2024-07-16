package bssm2024.demo.controller

import bssm2024.demo.dto.CreateUrlReq
import bssm2024.demo.dto.CreateUrlRes
import bssm2024.demo.model.Url
import bssm2024.demo.service.UrlService
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.BadRequestException
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class UrlController(val urlService: UrlService) {
    @PostMapping("/short-urls")
    fun createUrl(@RequestBody request: CreateUrlReq): CreateUrlRes {
        val original = request.original_url

        if(urlService.checkOriginal(original) != null) {
            throw BadRequestException()
        }
        val encodedUrl = urlService.create(original)

        val url = urlService.save(
            Url(
                originalUrl = original,
                encodedUrl = encodedUrl,
                clickCount = 0
            )
        )

        return CreateUrlRes(
            original_url = url.originalUrl,
            encoded_url = url.encodedUrl,
            created_at = url.createdAt
        )
    }

    @GetMapping("/short-urls")
    fun readUrls(@RequestParam(name = "createdAfter", required = false) time: LocalDateTime?): List<Url> {
        return if (time != null) {
            urlService.readAfterTime(time)
        } else {
            urlService.readDefault()
        }
    }

    @GetMapping("/short-url/{encoded}")
    fun redirect(@PathVariable encoded: String, response: HttpServletResponse) {
        val url = urlService.redirect(encoded)
        response.sendRedirect(url.originalUrl)
    }
}