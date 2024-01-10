package no.nav.klage.clients.dokdistkanal

import no.nav.klage.util.TokenUtil
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.klage.util.logErrorResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class DokDistKanalClient(
    private val dokDistKanalWebClient: WebClient,
    private val tokenUtil: TokenUtil,
) {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    fun bestemDistribusjonskanal(
        bestemDistribusjonskanalInput: BestemDistribusjonskanalInput
    ): BestemDistribusjonskanalResponse {
        logger.debug("Skal bestemme distribusjonskanal for $bestemDistribusjonskanalInput")

        return dokDistKanalWebClient.post()
            .header("Nav-Consumer-Id", applicationName)
            .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenUtil.getSaksbehandlerAccessTokenWithSafScope()}")
            .bodyValue(bestemDistribusjonskanalInput)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response ->
                logErrorResponse(response, ::bestemDistribusjonskanal.name, secureLogger)
            }
            .bodyToMono<BestemDistribusjonskanalResponse>()
            .block()
            ?: throw RuntimeException("Kunne ikke bestemme distribusjonskanal.")
    }
}