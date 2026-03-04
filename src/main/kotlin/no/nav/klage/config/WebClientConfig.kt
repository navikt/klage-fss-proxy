package no.nav.klage.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Bean
    fun connectionProvider(): ConnectionProvider {
        return ConnectionProvider.builder("custom")
            // Max idle time - evict connections that have been idle for too long
            .maxIdleTime(Duration.ofSeconds(20))
            // Max life time - evict connections regardless of activity after this time
            .maxLifeTime(Duration.ofMinutes(4))
            // Periodically check and evict connections that have been idle
            .evictInBackground(Duration.ofSeconds(30))
            .build()
    }

    @Bean
    fun reactorNettyHttpClient(connectionProvider: ConnectionProvider): HttpClient {
        val timeoutInSeconds = 200L
        return HttpClient.create(connectionProvider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
            // Enable TCP keep-alive to detect dead connections at OS level
            .option(ChannelOption.SO_KEEPALIVE, true)
            .responseTimeout(Duration.ofSeconds(timeoutInSeconds))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeoutInSeconds, TimeUnit.SECONDS))
                conn.addHandlerLast(WriteTimeoutHandler(timeoutInSeconds, TimeUnit.SECONDS))
            }
    }

    @Bean
    fun webClientBuilder(reactorNettyHttpClient: HttpClient): WebClient.Builder {
        val connector = ReactorClientHttpConnector(reactorNettyHttpClient)
        return WebClient.builder()
            .clientConnector(connector)
    }
}