package no.nav.klage.clients.foerstesidegenerator

import no.nav.klage.util.getSecureLogger
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class FoerstesidegeneratorClient(
    private val foerstesidegeneratorWebClient: WebClient,
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val secureLogger = getSecureLogger()
    }

    fun createFoersteside(): String {
        runCatching {
            foerstesidegeneratorWebClient.post()
                .header(HttpHeaders.AUTHORIZATION, )//"Bearer ${tokenUtil.getOnBehalfOfToken..()}"
//                .bodyValue()
                .retrieve()
                .bodyToMono<Unit>()
                .block() ?: throw RuntimeException("Response was null")

        }.onFailure {
            secureLogger.error("Could not create foersteside", it)
            throw RuntimeException("Could not create foersteside")
        }

        TODO() //loepenr
    }

    fun fetchFoersteside(loepenummer: String): ByteArray {
        runCatching {
            foerstesidegeneratorWebClient.post()
                .header(HttpHeaders.AUTHORIZATION, )//"Bearer ${tokenUtil.getOnBehalfOfToken..()}"
//                .bodyValue()
                .retrieve()
                .bodyToMono<Unit>()
                .block() ?: throw RuntimeException("Response was null")

        }.onFailure {
            secureLogger.error("Could not fetch foersteside", it)
            throw RuntimeException("Could not fetch foersteside")
        }

        TODO()
    }


}
