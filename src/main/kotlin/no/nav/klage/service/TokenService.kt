package no.nav.klage.service

import no.nav.klage.auth.currentAzureToken
import org.springframework.stereotype.Service

@Service
class TokenService {
    /** The incoming token, passed on unchanged to Klanke. */
    fun getToken(): String = checkNotNull(currentAzureToken()?.tokenValue) { "Token must be present" }
}
