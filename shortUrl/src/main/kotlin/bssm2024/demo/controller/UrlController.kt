package bssm2024.demo.controller

import bssm2024.demo.dto.CreateUrlReq
import bssm2024.demo.dto.CreateUrlRes
import bssm2024.demo.model.ShortenUrl
import bssm2024.demo.service.UrlService
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.BadRequestException
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
        return urlService.create(request.original_url)
    }

    @GetMapping("/short-urls")
    fun readUrls(): List<ShortenUrl>
        = urlService.readAll()

    @GetMapping("/short-url/{encoded}")
    fun redirect(@PathVariable encoded: String, response: HttpServletResponse) {
        val url = urlService.redirect(encoded) ?: throw NotFoundException()
        response.sendRedirect(url.originalUrl)
    }
}