package no.nav.klage.service

import no.nav.klage.config.SecurityConfiguration
import no.nav.security.token.support.core.context.TokenValidationContextHolder
import org.springframework.stereotype.Service

@Service
class TokenService(
        private val ctxHolder: TokenValidationContextHolder
) {

    fun getIdent(): String = ctxHolder.tokenValidationContext
            .getJwtToken(SecurityConfiguration.ISSUER_AAD).jwtTokenClaims?.get("NAVIdent")?.toString()
            ?: throw RuntimeException("Ident not found in token")

    fun getToken(): String {
        val token = ctxHolder.tokenValidationContext?.getJwtToken(SecurityConfiguration.ISSUER_AAD)?.tokenAsString
        return checkNotNull(token) { "Token must be present" }
    }

}
