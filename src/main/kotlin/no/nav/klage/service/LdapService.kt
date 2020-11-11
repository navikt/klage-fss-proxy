package no.nav.klage.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.naming.NamingEnumeration
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.LdapContext


@Service
class LdapService(
        private val navLdapContext: LdapContext,
        private val tokenService: TokenService
) {
    @Value("\${LDAP_BASEDN}")
    private lateinit var ldapBaseDn: String

    fun getRolesFor(ident: String): List<String> = getRoles(search(ident))
    fun getRolesForUser(): List<String> = getRoles(search(tokenService.getIdent()))

    private fun search(ident: String) = navLdapContext.search(
            "OU=Users,OU=NAV,OU=BusinessUnits,$ldapBaseDn",
            "(&(objectClass=user)(CN=${ident}))",
            SearchControls().apply {
                searchScope = SearchControls.SUBTREE_SCOPE
            }
    )

    private fun getRoles(result: NamingEnumeration<SearchResult>): List<String> {
            val attributes = result.next().attributes["memberof"].all
            return parseRoles(attributes)
    }

    private fun parseRoles(attributes: NamingEnumeration<*>): List<String> {
        val rawRoles: MutableList<String> = ArrayList()
        while (attributes.hasMore()) {
            rawRoles.add(attributes.next() as String)
        }
        return parseRole(rawRoles)
    }

    private fun parseRole(rawRoles: List<String>): List<String> =
            rawRoles.map {
                check(it.startsWith("CN=")) { "Error formatting AD-role: $it" }
                it.split(",")[0].split("CN=")[1]
            }
}
