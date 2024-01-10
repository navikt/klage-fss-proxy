package no.nav.klage.config

import no.nav.klage.util.getLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

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
            .defaultHeader("Nav-Consumer-Id", "klage-fss-proxy")
            .build()
    }
}