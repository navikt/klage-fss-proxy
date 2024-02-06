package no.nav.klage.api.controller

import no.nav.klage.api.controller.input.AssignedInKabalInput
import no.nav.klage.api.controller.input.FeilregistrertInKabalInput
import no.nav.klage.api.controller.input.GetSakWithSaksbehandlerIdent
import no.nav.klage.api.controller.input.HandledInKabalInput
import no.nav.klage.clients.klanke.*
import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.util.getLogger
import no.nav.klage.util.getSecureLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                sakstype = it.sakstype,
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

    @PostMapping("/saker/{sakId}/assignedinkabal")
    fun setAssignedInKabal(
        @PathVariable("sakId") sakId: String,
        @RequestBody assignedInKabalInput: AssignedInKabalInput,
    ) {
        secureLogger.debug("received setAssignedInKabal request for sak {}: {}", sakId, assignedInKabalInput)

        return klankeClient.setAssignedInKabal(
            sakId = sakId,
            input = no.nav.klage.clients.klanke.AssignedInKabalInput(
                saksbehandlerIdent = assignedInKabalInput.saksbehandlerIdent,
                enhetsnummer = assignedInKabalInput.enhetsnummer
            )
        )
    }

    @PostMapping("/saker/{sakId}/finished")
    fun setSakFinished(
        @PathVariable("sakId") sakId: String,
        @RequestBody sakFinishedInput: SakFinishedInput,
    ) {
        secureLogger.debug("received setSakFinished request for sak {}: {}", sakId, sakFinishedInput)

        return klankeClient.setSakFinished(
            sakId = sakId,
            input = sakFinishedInput,
        )
    }

    @PostMapping("/saker/{sakId}/feilregistrertinkabal")
    fun setFeilregistrertInKabal(
        @PathVariable("sakId") sakId: String,
        @RequestBody feilregistrertInKabalInput: FeilregistrertInKabalInput,
    ) {
        secureLogger.debug("received setFeilregistrertInKabal request for sak {}", sakId)

        return klankeClient.setFeilregistrertInKabal(
            sakId = sakId,
            input = no.nav.klage.clients.klanke.FeilregistrertInKabalInput(saksbehandlerIdent = feilregistrertInKabalInput.saksbehandlerIdent),
        )
    }

    @PostMapping("/saker/{sakId}")
    fun getSakAppAccess(
        @PathVariable("sakId") sakId: String,
        @RequestBody input: GetSakWithSaksbehandlerIdent,
    ): SakFromKlanke {
        secureLogger.debug("received getSakAppAccess request for sak {}", sakId)

        return klankeClient.getSakAppAccess(
            sakId = sakId,
            input = GetSakAppAccessInput(saksbehandlerIdent = input.saksbehandlerIdent),
        ).let {
            SakFromKlanke(
                sakId = it.sakId,
                fagsakId = it.fagsakId,
                tema = it.tema,
                utfall = it.utfall,
                enhetsnummer = it.enhetsnummer,
                vedtaksdato = LocalDate.parse(it.vedtaksdatoAsString, DateTimeFormatter.BASIC_ISO_DATE),
                fnr = it.fnr,
                sakstype = it.sakstype,
            )
        }
    }

    @GetMapping("/access")
    fun checkAccess(): Access {
        secureLogger.debug("check access")

        return klankeClient.checkAccess()
    }
}