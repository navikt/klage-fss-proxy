package no.nav.klage.service

import no.nav.klage.domain.LdapUser
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.query.LdapQueryBuilder
import org.springframework.ldap.query.SearchScope
import org.springframework.stereotype.Service


@Service
class LdapService(
        private val navLdapTemplate: LdapTemplate,
        private val tokenService: TokenService
) {

    fun getRolesForUser(): List<String> = getRoles(tokenService.getIdent())

    fun getRolesFor(ident: String): List<String> = getRoles(ident)

    private fun getRoles(ident: String) = search(ident)?.memberOf ?: listOf()

    private fun search(ident: String) = navLdapTemplate.findOne(
            LdapQueryBuilder.query().apply {
                searchScope(SearchScope.SUBTREE)
                where("objectClass").`is`("user")
                        .and("CN").`is`(ident)
            },
            LdapUser::class.java
    )
}
