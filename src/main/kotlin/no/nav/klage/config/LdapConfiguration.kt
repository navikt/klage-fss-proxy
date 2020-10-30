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

    private val env = Hashtable<String, String>()

    init {
        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.ldap.LdapCtxFactory"
        env[Context.SECURITY_AUTHENTICATION] = "simple"
        env[Context.PROVIDER_URL] = ldapUrl
        env[Context.SECURITY_PRINCIPAL] = username
        env[Context.SECURITY_CREDENTIALS] = password
    }

    @Bean
    fun getNavLdapContext(): LdapContext {
        return InitialLdapContext(env, null)
    }


}
