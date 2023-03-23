package no.nav.klage.api.controller

import no.nav.klage.clients.klanke.KlankeClient
import no.nav.klage.clients.klanke.KlankeSearchHit
import no.nav.klage.clients.klanke.KlankeSearchInput
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.*

@Profile("dev-fss")
@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
@RequestMapping("/klanke")
class KlankeProxyController(
    private val klankeClient: KlankeClient
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    @PostMapping("/search")
    fun searchKlanke(
        @RequestBody klankeSearchInput: KlankeSearchInput,
    ): List<KlankeSearchHit> {
        secureLogger.debug("received searchKlage request: {}", klankeSearchInput)

        return klankeClient.searchKlanke(klankeSearchInput)

    }
}