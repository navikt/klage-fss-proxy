package no.nav.klage.api

import no.nav.klage.config.SecurityConfiguration.Companion.ISSUER_AAD
import no.nav.klage.domain.AuditLogEvent
import no.nav.klage.service.LdapService
import no.nav.klage.util.AuditLogger
import no.nav.klage.util.getLogger
import no.nav.security.token.support.core.api.ProtectedWithClaims
import org.springframework.web.bind.annotation.*
import no.nav.klage.domain.view.AuditLogEvent as AuditLogEventView

@RestController
@ProtectedWithClaims(issuer = ISSUER_AAD)
class ProxyController(
    private val ldapService: LdapService,
    private val auditLogger: AuditLogger
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val logger = getLogger(javaClass.enclosingClass)
    }

    @GetMapping("/roller")
    fun getRoller(): List<String> = ldapService.getRolesForUser()

    @GetMapping("/roller/{ident}")
    fun getRollerForIdent(@PathVariable ident: String): List<String> = ldapService.getRolesFor(ident)

    @PostMapping("/auditlogs")
    fun auditLog(@RequestBody auditLogEvent: AuditLogEventView) {
        logger.debug("Receiving request to log to audit log")
        auditLogger.log(auditLogEvent.toAuditLogEvent())
    }

    private fun AuditLogEventView.toAuditLogEvent() = AuditLogEvent(
        applicationName = this.applicationName,
        navIdent = this.navIdent,
        requestURL = this.requestURL,
        requestMethod = this.requestMethod,
        personFnr = this.personFnr,
        traceId = this.traceId,
        logLevel = AuditLogEvent.Level.valueOf(this.logLevel.name),
        appDescription = this.appDescription
    )

}