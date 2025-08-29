package no.nav.klage.config

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException

@RestControllerAdvice
class ProxyErrorHandler {

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<ByteArray> {
        // Copy headers from downstream (avoid hop-by-hop headers that the server manages)
        val headers = HttpHeaders()
        headers.addAll(ex.headers)
        headers.remove(HttpHeaders.TRANSFER_ENCODING)
        headers.remove(HttpHeaders.CONTENT_LENGTH)

        return ResponseEntity
            .status(ex.statusCode)
            .headers(headers)
            .body(ex.responseBodyAsByteArray)
    }
}