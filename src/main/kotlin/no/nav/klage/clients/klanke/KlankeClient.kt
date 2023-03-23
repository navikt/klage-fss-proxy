package no.nav.klage.clients.klanke

import no.nav.klage.service.TokenService
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class KlankeClient(
    private val klankeWebClient: WebClient,
    private val tokenService: TokenService
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    fun searchKlanke(klankeSearchInput: KlankeSearchInput): List<KlankeSearchHit> {
        secureLogger.debug("Token sent to klanke: ${tokenService.getToken()}")
        return klankeWebClient.post()
            .uri { it.path("/api/saker.rest").build() }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(klankeSearchInput)
            .retrieve()
            .bodyToMono<List<KlankeSearchHit>>()
            .block() ?: throw RuntimeException("Response was null")
    }
}

