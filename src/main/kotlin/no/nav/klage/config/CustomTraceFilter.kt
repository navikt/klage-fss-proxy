package no.nav.klage.config

import io.micrometer.tracing.Tracer
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

/**
 * Adding some custom NAV-specific attributes to standard Spring Sleuth
 */
@Component
@Profile("!local")
@Order(-20)
class CustomTraceFilter(
    @Autowired private val tracer: Tracer,
    @Value("\${spring.application.name}") private val appName: String,
    @Value("\${navCallId}") private val navCallIdFieldName: String,
    @Value("\${navConsumerId}") private val navConsumerIdFieldName: String

) : GenericFilterBean() {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    override fun doFilter(
        request: ServletRequest?, response: ServletResponse,
        chain: FilterChain
    ) {
        tracer.createBaggage(navCallIdFieldName, tracer.currentTraceContext().context()!!.traceId())

        chain.doFilter(request, response)
    }
}