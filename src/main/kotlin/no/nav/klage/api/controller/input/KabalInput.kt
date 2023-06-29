package no.nav.klage.api.controller.input

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class HandledInKabalInput(
    val fristAsString: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AssignedInKabalInput(
    val saksbehandlerIdent: String,
    val enhetsnummer: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeilregistrertInKabalInput(
    val saksbehandlerIdent: String,
)