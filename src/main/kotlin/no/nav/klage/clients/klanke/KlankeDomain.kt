package no.nav.klage.clients.klanke

data class KlankeSearchInput(
    val fnr: String
)

data class KlankeSearchOutput(
    val klankeSearchHits: Set<KlankeSearchHit>
)

data class KlankeSearchHit(
    val sakId: String
)