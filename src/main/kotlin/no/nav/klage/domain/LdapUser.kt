package no.nav.klage.domain

import org.springframework.ldap.odm.annotations.Attribute
import org.springframework.ldap.odm.annotations.Entry
import org.springframework.ldap.odm.annotations.Id
import javax.naming.Name

@Entry(objectClasses = ["user"])
data class LdapUser(
        @Id
        val dn: Name,
        @Attribute(name = "givenname" )
        val fornavn: String,
        val sn: String,
        val displayName: String,
        val mail: String,
        @Attribute(name = "SamAccountName" )
        val ident: String,
        val memberOf: List<String>,
        @Attribute(name = "office" )
        val navkontor: String,
        @Attribute(name = "streetAddress" )
        val navkontorId: String
)
