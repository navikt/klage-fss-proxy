package no.nav.klage.clients.klanke

import java.time.LocalDate

data class KlankeSearchInput(
    val fnr: String,
    val sakstype: String,
)

data class KlankeSearchHit(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdatoAsString: String,
    val fnr: String,
)

data class SakFromKlanke(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdato: LocalDate,
    val fnr: String,
)

data class HandledInKabalInput(
    //aka frist
    val svardatoAsString: String
)