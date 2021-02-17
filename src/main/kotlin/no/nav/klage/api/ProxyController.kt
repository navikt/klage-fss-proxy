package no.nav.klage.api

import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.service.LdapService
import no.nav.klage.util.getLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.web.bind.annotation.*

@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
class ProxyController(
    private val ldapService: LdapService
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @GetMapping("/roller")
    fun getRoller(): List<String> = ldapService.getRolesForUser()

    @GetMapping("/roller/{ident}")
    fun getRollerForIdent(@PathVariable ident: String): List<String> = ldapService.getRolesFor(ident)
}