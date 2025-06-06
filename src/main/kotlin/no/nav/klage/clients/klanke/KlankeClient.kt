package no.nav.klage.clients.klanke

import no.nav.klage.service.TokenService
import no.nav.klage.util.getLogger
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
    }

    fun searchKlanke(klankeSearchInput: KlankeSearchInput): List<KlankeSearchHit> {
        return klankeWebClient.post()
            .uri { it.path("/api/saker.rest").build() }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(klankeSearchInput)
            .retrieve()
            .bodyToMono<List<KlankeSearchHit>>()
            .block() ?: throw RuntimeException("Response was null")
    }

    fun setHandledInKabal(sakId: String, input: HandledInKabalInput) {
        klankeWebClient.post()
            .uri { it.path("/api/saker/{sakId}/handledinkabal.rest").build(sakId) }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(input)
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun setAssignedInKabal(sakId: String, input: AssignedInKabalInput) {
        klankeWebClient.post()
            .uri { it.path("/api/saker/{sakId}/assignedinkabal.rest").build(sakId) }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(input)
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun setSakFinished(sakId: String, input: SakFinishedInput) {
        klankeWebClient.post()
            .uri { it.path("/api/saker/{sakId}/finished.rest").build(sakId) }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(input)
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun setFeilregistrertInKabal(sakId: String, input: FeilregistrertInKabalInput) {
        klankeWebClient.post()
            .uri { it.path("/api/saker/{sakId}/feilregistrert.rest").build(sakId) }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(input)
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    fun getSakAppAccess(sakId: String, input: GetSakAppAccessInput): KlankeSearchHit {
        return klankeWebClient.post()
            .uri { it.path("/api/saker/{sakId}/detailsappaccess.rest").build(sakId) }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .bodyValue(input)
            .retrieve()
            .bodyToMono<KlankeSearchHit>()
            .block() ?: throw RuntimeException("Response was null")
    }

    fun checkAccess(): Access {
        return klankeWebClient.get()
            .uri { it.path("/api/access.rest").build() }
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
            .retrieve()
            .bodyToMono<Access>()
            .block() ?: throw RuntimeException("Response was null")
    }
}

