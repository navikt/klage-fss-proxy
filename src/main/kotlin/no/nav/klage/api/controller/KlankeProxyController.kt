package no.nav.klage.api.controller

import no.nav.klage.api.controller.input.HandledInKabalInput
import no.nav.klage.clients.klanke.KlankeClient
import no.nav.klage.clients.klanke.KlankeSearchInput
import no.nav.klage.clients.klanke.SakFromKlanke
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Profile("dev-fss")
@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
@RequestMapping("/klanke")
class KlankeProxyController(
    private val klankeClient: KlankeClient
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
        private val secureLogger = getSecureLogger()
    }

    @PostMapping("/saker")
    fun searchKlanke(
        @RequestBody klankeSearchInput: KlankeSearchInput,
    ): List<SakFromKlanke> {
        secureLogger.debug("received searchKlanke request: {}", klankeSearchInput)

        return klankeClient.searchKlanke(klankeSearchInput).map {
            SakFromKlanke(
                sakId = it.sakId,
                fagsakId = it.fagsakId,
                tema = it.tema,
                utfall = it.utfall,
                enhetsnummer = it.enhetsnummer,
                vedtaksdato = LocalDate.parse(it.vedtaksdatoAsString, DateTimeFormatter.BASIC_ISO_DATE),
                fnr = it.fnr,
            )
        }
    }

    @PostMapping("/saker/{sakId}/handledinkabal")
    fun setHandledInKabal(
        @PathVariable("sakId") sakId: String,
        @RequestBody handledInKabalInput: HandledInKabalInput,
    ) {
        secureLogger.debug("received setHandledInKabal request for sak {}: {}", sakId, handledInKabalInput)

        return klankeClient.setHandledInKabal(
            sakId = sakId,
            input = no.nav.klage.clients.klanke.HandledInKabalInput(svardatoAsString = handledInKabalInput.fristAsString)
        )
    }

    @GetMapping("/saker/{sakId}")
    fun getSak(
        @PathVariable("sakId") sakId: String,
    ): SakFromKlanke {
        secureLogger.debug("received getSak request for sak {}", sakId)

        return klankeClient.getSak(
            sakId = sakId,
        ).let {
            SakFromKlanke(
                sakId = it.sakId,
                fagsakId = it.fagsakId,
                tema = it.tema,
                utfall = it.utfall,
                enhetsnummer = it.enhetsnummer,
                vedtaksdato = LocalDate.parse(it.vedtaksdatoAsString, DateTimeFormatter.BASIC_ISO_DATE),
                fnr = it.fnr,
            )
        }
    }
}