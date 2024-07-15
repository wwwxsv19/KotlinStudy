package bssm2024.demo.controller

import bssm2024.demo.dto.CreateUrlReq
import bssm2024.demo.dto.CreateUrlRes
import bssm2024.demo.model.Url
import bssm2024.demo.service.UrlService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlController(val urlService: UrlService) {
    @PostMapping("/short-urls")
    fun createUrl(@RequestBody request: CreateUrlReq): CreateUrlRes {
        val encodedUrl = urlService.create(request.original_url)

        val url = urlService.save(
            Url(
                originalUrl = request.original_url,
                encodedUrl = encodedUrl,
                clickCount = 0
            )
        )

        return CreateUrlRes(
            original_url = url.originalUrl,
            encoded_url = url.encodedUrl
        )
    }

    @GetMapping("/short-urls")
    fun readUrls(): List<Url>
        = urlService.readAll()

    @GetMapping("/short-url/{encoded}")
    fun redirect(@PathVariable encoded: String, response: HttpServletResponse) {
        val url = urlService.redirect(encoded)
        response.sendRedirect(url.originalUrl)
    }
}