package no.nav.klage.api.controller.input

data class HandledInKabalInput(
    val fristAsString: String,
)

data class AssignedInKabalInput(
    val saksbehandlerIdent: String,
)

data class FeilregistrertInKabalInput(
    val saksbehandlerIdent: String,
)