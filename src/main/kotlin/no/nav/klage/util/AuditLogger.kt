package no.nav.klage.util

import no.nav.klage.domain.AuditLogEvent
import no.nav.klage.domain.AuditLogEvent.Level.INFO
import no.nav.klage.domain.AuditLogEvent.Level.WARN
import org.springframework.stereotype.Component
import java.lang.String.join

@Component
class AuditLogger {

    companion object {
        val auditLogger = getAuditLogger()
    }

    fun log(logEvent: AuditLogEvent) {
        when (logEvent.logLevel) {
            WARN -> {
                auditLogger.warn(compileLogMessage(logEvent))
            }
            INFO -> {
                auditLogger.info(compileLogMessage(logEvent))
            }
        }
    }

    private fun compileLogMessage(logEvent: AuditLogEvent): String {
        val version = "CEF:0"
        val deviceVendor = logEvent.applicationName
        val deviceProduct = "auditLog"
        val deviceVersion = "1.0"
        val deviceEventClassId = "${logEvent.applicationName}:accessed"
        val name = logEvent.appDescription
        val severity = logEvent.logLevel.name

        val extensions = join(" ", getExtensions(logEvent))

        return join(
            "|", listOf(
                version,
                deviceVendor,
                deviceProduct,
                deviceVersion,
                deviceEventClassId,
                name,
                severity,
                extensions
            )
        )
    }

    private fun getExtensions(logEvent: AuditLogEvent): List<String> {
        val extensions = mutableListOf<String>()
        extensions += "end=${System.currentTimeMillis()}"
        extensions += "suid=${logEvent.navIdent}"
        extensions += "duid=${logEvent.personFnr}"
        extensions += "request=${logEvent.requestURL}"
        extensions += "requestMethod=${logEvent.requestMethod}"
        extensions += "flexString1=Permit"
        extensions += "flexString1Label=Decision"
        extensions += "sproc=${logEvent.traceId}}"
        return extensions
    }
}