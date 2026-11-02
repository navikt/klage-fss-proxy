package no.nav.klage.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class TokenServiceTest {
    private val tokenService = TokenService()

    @AfterEach
    fun clearContext() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `returns the incoming token unchanged`() {
        val jwt =
            Jwt
                .withTokenValue("incoming-token")
                .header("alg", "RS256")
                .claim("sub", "subject")
                .build()
        SecurityContextHolder.getContext().authentication = JwtAuthenticationToken(jwt)

        assertThat(tokenService.getToken()).isEqualTo("incoming-token")
    }

    @Test
    fun `fails when there is no token`() {
        assertThrows<IllegalStateException> { tokenService.getToken() }
    }
}
