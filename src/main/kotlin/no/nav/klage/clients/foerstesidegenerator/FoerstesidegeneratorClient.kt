package no.nav.klage.clients.foerstesidegenerator

import no.nav.klage.clients.foerstesidegenerator.domain.PostFoerstesideRequest
import no.nav.klage.clients.foerstesidegenerator.domain.PostFoerstesideResponse
import no.nav.klage.service.TokenService
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class FoerstesidegeneratorClient(
    private val foerstesidegeneratorWebClient: WebClient,
    private val tokenService: TokenService,
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    fun createFoersteside(postFoerstesideRequest: PostFoerstesideRequest): String {
        runCatching {
            val res = foerstesidegeneratorWebClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ${tokenService.getToken()}")
                .bodyValue(postFoerstesideRequest)
                .retrieve()
                .bodyToMono<PostFoerstesideResponse>()
                .block() ?: throw RuntimeException("Response was null")
            logger.debug("result from foerstesidegenerator: {}", res)

            return res.loepenummer ?: error("missing loepenr")
        }.onFailure {
            secureLogger.error("Could not create foersteside", it)
            throw RuntimeException("Could not create foersteside")
        }
        error("?")
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
