package no.nav.klage.clients.klanke

data class KlankeSearchInput(
    val fnr: String,
    val dummy: String? = ""
)

data class KlankeSearchHit(
    val sakId: String
)