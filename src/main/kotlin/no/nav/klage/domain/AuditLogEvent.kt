package no.nav.klage.domain

data class AuditLogEvent(
    val applicationName: String,
    val navIdent: String,
    val requestURL: String,
    val requestMethod: String,
    val personFnr: String?,
    val traceId: String,
    val logLevel: Level = Level.INFO,
    val appDescription: String
) {
    enum class Level {
        INFO, WARN
    }
}