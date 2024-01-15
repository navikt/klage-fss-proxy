package no.nav.klage.config

import no.nav.klage.util.getLogger
import no.nav.security.token.support.client.spring.oauth2.DefaultOAuth2HttpClient
import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.http.io.SocketConfig
import org.apache.hc.core5.http.protocol.HttpContext
import org.apache.hc.core5.util.Timeout
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient

@Bean
@Primary
fun oAuth2HttpClient(): DefaultOAuth2HttpClient {
    getLogger(DefaultOAuth2HttpClient::class.java).debug("Using custom oAuth2HttpClient")
    val connectTimeout: Long = 15000
    val socketTimeout: Long = 15000
    val requestTimeout: Long = 15000

    val proxy = HttpHost("webproxy-nais.nav.no", 8088)
    val client: HttpClient =
        HttpClientBuilder.create()
            .setDefaultRequestConfig(
                RequestConfig.custom()
                    .setConnectTimeout(Timeout.ofSeconds(connectTimeout))
                    .setConnectionRequestTimeout(Timeout.ofSeconds(requestTimeout))
                    .build(),
            )
            .setConnectionManager(
                PoolingHttpClientConnectionManagerBuilder.create()
                    .setDefaultSocketConfig(
                        SocketConfig.custom()
                            .setSoTimeout(Timeout.ofMilliseconds(socketTimeout))
                            .build(),
                    )
                    .build(),
            )
            .setRoutePlanner(
                object : DefaultProxyRoutePlanner(proxy) {
                    public override fun determineProxy(
                        target: HttpHost,
                        context: HttpContext,
                    ): HttpHost? {
                        return if (target.hostName.contains("microsoft")) {
                            getLogger(javaClass.enclosingClass).debug("Determining proxy.")
                            getLogger(javaClass.enclosingClass).debug("Target: {}", target)
                            getLogger(javaClass.enclosingClass).debug("Context: {}", context)
                            super.determineProxy(target, context)
                        } else {
                            null
                        }
                    }
                },
            ).build()


    val requestFactory = HttpComponentsClientHttpRequestFactory(client)
    val restClient = RestClient.builder()
        .requestFactory(requestFactory)
        .build()
    return DefaultOAuth2HttpClient(restClient)
}