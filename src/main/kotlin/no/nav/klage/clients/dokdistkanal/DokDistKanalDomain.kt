package no.nav.klage.clients.dokdistkanal

data class BestemDistribusjonskanalInput(
//    val dokumentTypeId: String,
    val mottakerId: String,
    val mottakerType: MottakerType,
    val brukerId: String,
    val erArkivert: Boolean,
    val tema: String,
)

enum class MottakerType {
    PERSON,
    ORGANISASJON,
    SAMHANDLER_HPR,
    SAMHANDLER_UTL_ORG,
    SAMHANDLER_UKJENT
}

data class BestemDistribusjonskanalResponse(
    val distribusjonskanal: Distribusjonskanal
)

enum class Distribusjonskanal {
    LOKAL_PRINT,
    PRINT,
    DITT_NAV,
    SDP,
    INGEN_DISTRIBUSJON,
    TRYGDERETTEN,
    DPVT
}