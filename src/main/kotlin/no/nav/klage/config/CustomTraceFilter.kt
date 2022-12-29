package no.nav.klage.config

import brave.baggage.BaggageField
import io.micrometer.tracing.Tracer
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
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

    override fun doFilter(
        request: ServletRequest?, response: ServletResponse,
        chain: FilterChain
    ) {
        val currentSpan = tracer.currentSpan()

        if (currentSpan != null) {
            BaggageField.create(navCallIdFieldName).updateValue(currentSpan.context().traceId())
            BaggageField.create(navConsumerIdFieldName).updateValue(appName)
        }

        chain.doFilter(request, response)
    }
}