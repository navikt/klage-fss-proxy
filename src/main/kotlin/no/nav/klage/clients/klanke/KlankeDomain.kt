package no.nav.klage.clients.klanke

data class KlankeSearchInput(
    val fnr: String
)

data class KlankeSearchHit(
    val sakId: String,
)