package no.nav.klage.api

import no.nav.klage.clients.foerstesidegenerator.FoerstesidegeneratorClient
import no.nav.klage.clients.foerstesidegenerator.domain.PostFoerstesideRequest
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
class ProxyController(private val foerstesidegeneratorClient: FoerstesidegeneratorClient) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @PostMapping("/foersteside")
    fun createFoersteside(
        @RequestBody postFoerstesideRequest: PostFoerstesideRequest,
    ) {
        logger.debug("received postFoerstesideRequest: {}", postFoerstesideRequest)
        foerstesidegeneratorClient.createFoersteside(postFoerstesideRequest)
    }
}