package no.nav.klage.service

import no.nav.klage.config.SecurityConfiguration
import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Service

@Service
class TokenService(
        private val tokenValidationContextHolder: TokenValidationContextHolder
) {

    fun getIdent(): String = tokenValidationContextHolder.tokenValidationContext
            .getJwtToken(SecurityConfiguration.ISSUER_AAD).jwtTokenClaims?.get("todo")?.toString()
            ?: throw RuntimeException("Ident not found in token")

}
