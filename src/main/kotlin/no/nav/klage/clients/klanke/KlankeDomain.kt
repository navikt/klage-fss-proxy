package no.nav.klage.clients.klanke

import java.time.LocalDate

data class KlankeSearchInput(
    val fnr: String
)

data class KlankeSearchHit(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdatoAsString: String,
)

data class SakFromKlanke(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdatoAsString: LocalDate,
)