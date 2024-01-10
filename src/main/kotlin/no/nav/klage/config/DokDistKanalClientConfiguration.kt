package no.nav.klage.config

import no.nav.klage.util.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class DokDistKanalClientConfiguration(
    private val webClientBuilder: WebClient.Builder
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @Value("\${DOK_DIST_KANAL_URL}")
    private lateinit var dokDistKanalURL: String

    @Bean
    fun dokDistKanalWebClient(): WebClient {
        return webClientBuilder
            .baseUrl(dokDistKanalURL)
            .build()
    }
}