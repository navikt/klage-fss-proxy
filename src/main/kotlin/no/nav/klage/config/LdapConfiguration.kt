package no.nav.klage.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import java.util.*
import javax.naming.Context
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext

@Controller
class LdapConfiguration {
    @Value("\${LDAP_URL}")
    private lateinit var ldapUrl: String

    @Value("\${LDAP_USERNAME}")
    private lateinit var username: String

    @Value("\${LDAP_PASSWORD}")
    private lateinit var password: String

    @Bean
    fun getNavLdapContext(): LdapContext {
        return InitialLdapContext(getLdapEnv(), null)
    }

    private fun getLdapEnv() = Hashtable<String, String>().apply {
        put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        put(Context.SECURITY_AUTHENTICATION, "simple")
        put(Context.PROVIDER_URL, ldapUrl)
        put(Context.SECURITY_PRINCIPAL, username)
        put(Context.SECURITY_CREDENTIALS, password)
    }


}
