package no.nav.klage.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.ldap.core.LdapTemplate
import org.springframework.ldap.core.support.LdapContextSource
import org.springframework.ldap.pool.factory.PoolingContextSource
import org.springframework.ldap.pool.validation.DefaultDirContextValidator
import org.springframework.stereotype.Controller

@Controller
@ImportAutoConfiguration(LdapAutoConfiguration::class)
class LdapConfiguration {

    @Autowired
    private lateinit var navContextSource: LdapContextSource

    @Bean
    fun poolingLdapContextSource() = PoolingContextSource().apply {
        dirContextValidator = DefaultDirContextValidator()
        contextSource = navContextSource
        testOnBorrow = true
        testWhileIdle = true
    }

    @Bean
    fun navLdapTemplate() = LdapTemplate(poolingLdapContextSource()).apply {
        setIgnorePartialResultException(true)
    }

}
