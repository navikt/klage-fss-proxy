package no.nav.klage.clients.klanke

data class ProxyKlankeSearchInput(
    val fnr: String,
    val dummy: String? = ""
)

data class KlankeSearchInput(
    val fnr: String
)

data class KlankeSearchHit(
    val sakId: String
)