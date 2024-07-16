package bssm2024.demo.service

import bssm2024.demo.model.Url
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.*

@SpringBootTest
class UrlServiceTest(
    @Autowired private val sut: UrlService,
    @Autowired private val urlRepository: UrlRepository
) {

    @BeforeTest
    fun setUp() {
        urlRepository.deleteAll()
    }

    @Test
    fun testSave() {
        val url = Url(
            originalUrl = "https://naver.com",
            encodedUrl = "http://localhost:8082/api/short-url/-3h0o8a8",
            clickCount = 0
        )

        val result = sut.save(url);

        assertEquals(url.originalUrl, result.originalUrl)
        assertEquals(url.encodedUrl, result.encodedUrl)
        assertNotNull(result.num)

    }

    @Test
    fun testFindAll() {
        val urls = urlRepository.saveAll(
            listOf(
                Url(
                    originalUrl = "https://naver.com",
                    encodedUrl = "http://localhost:8082/api/short-url/-3h0o8a8",
                    clickCount = 0
                ),
                Url(
                    originalUrl = "https://naver.com1",
                    encodedUrl = "http://localhost:8082/api/short-url/-3h0o8a83",
                    clickCount = 0
                ),
                Url(
                    originalUrl = "https://naver.com2",
                    encodedUrl = "http://localhost:8082/api/short-url/-3h0o8a84",
                    clickCount = 0
                )
            )
        )

        val result = sut.readAll()

        assertEquals(result.size, 3)
        assertEquals(result[0].originalUrl, urls[0].originalUrl)
        assertEquals(result[0].encodedUrl, urls[0].encodedUrl)
        assertNotNull(result[0].num)
    }

    @Test
    fun testFindByEncoded() {
        val encoded = "http://localhost:8082/api/short-url/-3h0o8a8"

        val saved = urlRepository.save(
            Url(
                originalUrl = "https://naver.com",
                encodedUrl = encoded,
                clickCount = 0
            )
        )

        val result = requireNotNull(sut.redirect("-3h0o8a8"))

        assertEquals(saved.originalUrl, result.originalUrl)
        assertEquals(saved.encodedUrl, result.encodedUrl)
        assertNotNull(result.num)
    }

    @Test
    fun testCreate() {
        val original = "https://naver.com"
        val result = sut.create(original)

        assertEquals("http://localhost:8082/api/short-url/" + original.hashCode().toString(radix = 26), result)
    }

    @Test
    fun testClickCount() {
        val encoded = "http://localhost:8082/api/short-url/-3h0o8a8"

        urlRepository.save(
            Url(
                originalUrl = "https://naver.com",
                encodedUrl = encoded,
                clickCount = 0
            )
        )
        sut.redirect("-3h0o8a8")
        sut.redirect("-3h0o8a8")
        sut.redirect("-3h0o8a8")

        val result = requireNotNull(urlRepository.findByEncodedUrl(encoded))

        assertEquals(encoded, result.encodedUrl)
        assertEquals(result.clickCount, 3)

    }
}