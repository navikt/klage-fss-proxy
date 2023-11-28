package no.nav.klage.clients.klanke

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class KlankeSearchInput(
    val fnr: String,
    val sakstype: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KlankeSearchHit(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdatoAsString: String,
    val fnr: String,
    val sakstype: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SakFromKlanke(
    val sakId: String,
    val fagsakId: String,
    val tema: String,
    val utfall: String,
    val enhetsnummer: String,
    val vedtaksdato: LocalDate,
    val fnr: String,
    val sakstype: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class HandledInKabalInput(
    //aka frist
    val svardatoAsString: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AssignedInKabalInput(
    val saksbehandlerIdent: String,
    val enhetsnummer: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeilregistrertInKabalInput(
    val saksbehandlerIdent: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SakFinishedInput(
    val status: Status,
    val nivaa: Nivaa,
    val typeResultat: TypeResultat,
    val utfall: Utfall,
    val mottaker: Mottaker,
    val saksbehandlerIdent: String,
)

enum class Status {
    RETURNERT_TK,
    VIDERESENDT_TR,
}

enum class Nivaa {
    KA, TR
}

enum class TypeResultat {
    RESULTAT, INNSTILLING_2
}

enum class Utfall {
    AVSLAG,
    AVSLAG_GODKJENNING,
    AVVIST_KLAGE,
    ADVARSEL,
    DELVIS_GODKJENNING,
    DELVIS_INNVILGET,
    DELVIS_TILBAKEBETALING,
    GODKJENNING,
    HENLAGT,
    HENLAGT_BORTFALT,
    INNVILGET,
    IKKE_BEHANDLET,
    IKKE_STRAFFBART,
    IKKE_TILBAKEBETALING,
    HJEMVIST_FOR_NY_BEHANDLING,
    POLITIANMELDELSE,
    TILBAKEBETALING,
    TVANGSGEBYR_FASTHOLDES,
    IKKE_BRUK
}

enum class Mottaker {
    TRYGDEKONTOR, TRYGDERETTEN
}

data class Access(
    val access: Boolean
)