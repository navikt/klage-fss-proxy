package no.nav.klage.domain

import org.springframework.ldap.odm.annotations.Attribute
import org.springframework.ldap.odm.annotations.Entry
import org.springframework.ldap.odm.annotations.Id
import javax.naming.Name

@Entry(objectClasses = ["user"])
class LdapUser {
        @Id
        var dn: Name? = null

        @Attribute(name = "givenname")
        var fornavn: String? = null
        var sn: String? = null
        var displayName: String? = null
        var mail: String? = null

        @Attribute(name = "SamAccountName")
        var ident: String? = null
        var memberOf: List<String> = emptyList()

        @Attribute(name = "office")
        var navkontor: String? = null

        @Attribute(name = "streetAddress")
        var navkontorId: String? = null

}
