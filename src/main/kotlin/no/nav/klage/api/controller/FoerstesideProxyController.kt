package no.nav.klage.api.controller

import no.nav.klage.clients.foerstesidegenerator.FoerstesideRequest
import no.nav.klage.clients.foerstesidegenerator.FoerstesidegeneratorClient
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
class FoerstesideProxyController(private val foerstesidegeneratorClient: FoerstesidegeneratorClient) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    @PostMapping("/genererfoersteside")
    @ResponseBody
    fun createFoersteside(
        @RequestBody foerstesideRequest: FoerstesideRequest,
    ): ResponseEntity<ByteArray> {
        secureLogger.debug("received foerstesideRequest: {}", foerstesideRequest)

        val data = foerstesidegeneratorClient.createFoersteside(foerstesideRequest)

        val responseHeaders = HttpHeaders()
        responseHeaders.contentType = MediaType.APPLICATION_PDF
        responseHeaders.add("Content-Disposition", "inline; filename=foersteside.pdf")
        return ResponseEntity(
            data,
            responseHeaders,
            HttpStatus.OK
        )
    }
}