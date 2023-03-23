package no.nav.klage.api.controller

import no.nav.klage.clients.klanke.*
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
        @RequestBody proxyKlankeSearchInput: ProxyKlankeSearchInput,
    ): List<KlankeSearchHitProper> {
        secureLogger.debug("received searchKlage request: {}", proxyKlankeSearchInput)

        return klankeClient.searchKlanke(KlankeSearchInput(fnr = proxyKlankeSearchInput.fnr)).map { KlankeSearchHitProper(sakId = it.sakId) }
    }
}