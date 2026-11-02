package no.nav.klage.config

import no.nav.klage.api.controller.KlankeProxyController
import no.nav.klage.auth.test.AzureSecurityContractTest
import no.nav.klage.clients.klanke.KlankeClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(KlankeProxyController::class)
@Import(SecurityConfiguration::class)
class SecurityConfigurationTest : AzureSecurityContractTest() {
    @MockitoBean
    lateinit var klankeClient: KlankeClient

    override fun protectedRequest() =
        post("/klanke/saker/123/handledinkabal")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{"fristAsString":"20260101"}""")
}
