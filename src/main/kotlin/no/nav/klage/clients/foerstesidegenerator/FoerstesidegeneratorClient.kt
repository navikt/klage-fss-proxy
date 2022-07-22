package no.nav.klage.clients.foerstesidegenerator

import no.nav.klage.clients.sts.StsClient
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class FoerstesidegeneratorClient(
    private val foerstesidegeneratorWebClient: WebClient,
    private val stsClient: StsClient,
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    fun createFoersteside(foerstesideRequest: FoerstesideRequest): ByteArray {
        runCatching {
            val res = foerstesidegeneratorWebClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ${stsClient.oidcToken()}")
                .bodyValue(foerstesideRequest)
                .retrieve()
                .bodyToMono<FoerstesideResponse>()
                .block() ?: throw RuntimeException("Response was null")

            logger.debug("Foersteside generated with loepenummer: {}", res.loepenummer)

            return res.foersteside
        }.onFailure {
            secureLogger.error("Could not create foersteside", it)
        }
        throw RuntimeException("Could not create foersteside. See secure logs.")
    }
}