package no.nav.klage.config

import no.nav.klage.util.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Controller
import java.util.*
import javax.naming.Context
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext

@Controller
class LdapConfiguration {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @Value("\${LDAP_URL}")
    private lateinit var ldapUrl: String

    @Value("\${LDAP_USERNAME}")
    private lateinit var username: String

    @Value("\${LDAP_PASSWORD}")
    private lateinit var password: String

    @Bean
    fun getNavLdapContext(): LdapContext {
        logger.info("LDAP: $username and ${password.length} and ${password.substring(0, 2)} and ${password.substring(password.length - 2)}")
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
