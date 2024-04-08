package no.nav.klage.clients.foerstesidegenerator

data class FoerstesideRequest(
    val spraakkode: Spraakkode = Spraakkode.NB,
    val adresse: Adresse? = null,
    val netsPostboks: String? = null,
    val avsender: Avsender? = null,
    val bruker: Bruker? = null,
    val ukjentBrukerPersoninfo: String? = null,
    val tema: String? = null,
    val behandlingstema: String? = null,
    val arkivtittel: String? = null,
    val vedleggsliste: List<String> = ArrayList(),
    val navSkjemaId: String? = null,
    val overskriftstittel: String? = null,
    val dokumentlisteFoersteside: List<String> = ArrayList(),
    val foerstesidetype: Foerstesidetype? = null,
    val enhetsnummer: String? = null,
    val arkivsak: Arkivsak? = null
) {

    enum class Spraakkode {
        NB,
        NN,
        EN,
    }

    data class Adresse(
        val adresselinje1: String? = null,
        val adresselinje2: String? = null,
        val adresselinje3: String? = null,
        val postnummer: String? = null,
        val poststed: String? = null
    )

    class Avsender(
        val avsenderId: String? = null,
        val avsenderNavn: String? = null
    )

    data class Arkivsak(
        val arkivsaksystem: Arkivsaksystem? = null,
        val arkivsaksnummer: String? = null
    ) {
        enum class Arkivsaksystem {
            GSAK,
            PSAK
        }
    }

    data class Bruker(
        val brukerId: String? = null,
        val brukerType: Brukertype? = null
    ) {
        enum class Brukertype {
            PERSON,
            ORGANISASJON
        }
    }

    enum class Foerstesidetype {
        ETTERSENDELSE,
        LOESPOST,
        SKJEMA,
        NAV_INTERN
    }
}

data class FoerstesideResponse(
    val foersteside: ByteArray,
    val loepenummer: String?,
)