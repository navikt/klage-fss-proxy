package no.nav.klage.api.controller

import no.nav.klage.clients.dokdistkanal.BestemDistribusjonskanalInput
import no.nav.klage.clients.dokdistkanal.BestemDistribusjonskanalResponse
import no.nav.klage.clients.dokdistkanal.DokDistKanalClient
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
//@ProtectedWithClaims(issuer = ISSUER_AAD)
//TODO: Unprotected only for testing
@Unprotected
class DokDistKanalProxyController(
    private val dokDistKanalClient: DokDistKanalClient
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    @PostMapping("/bestemdistribusjonskanal")
    @ResponseBody
    fun bestemDistribusjonskanal(
        @RequestBody bestemDistribusjonskanalInput: BestemDistribusjonskanalInput,
    ): BestemDistribusjonskanalResponse {
        secureLogger.debug("received bestemDistribusjonskanalInput: {}", bestemDistribusjonskanalInput)
        return dokDistKanalClient.bestemDistribusjonskanal(bestemDistribusjonskanalInput = bestemDistribusjonskanalInput)
    }
}