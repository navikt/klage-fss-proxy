package no.nav.klage.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Bean
    fun reactorNettyHttpClient(): HttpClient {
        val timeoutInSeconds = 200L
        return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000)
            .responseTimeout(Duration.ofSeconds(timeoutInSeconds))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(timeoutInSeconds, TimeUnit.SECONDS))
                conn.addHandlerLast(WriteTimeoutHandler(timeoutInSeconds, TimeUnit.SECONDS))
            }
    }

    @Bean
    fun webClientBuilder(httpClient: HttpClient): WebClient.Builder {
        val connector = ReactorClientHttpConnector(httpClient)
        return WebClient.builder()
            .clientConnector(connector)
    }
}