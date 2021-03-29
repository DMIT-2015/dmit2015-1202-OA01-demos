package dmit2015.config;

import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                useForwardToLogin = false,
                errorPage = ""
        )
)

@EmbeddedIdentityStoreDefinition({
        @Credentials(callerName = "dev01@dmit2015.ca", password = "Password2015", groups = {"DEVELOPER"})
})

@LdapIdentityStoreDefinition(
        url = "ldap://192.168.101.139:389",
        callerSearchBase = "ou=Departments,dc=dmit2015,dc=ca",
        callerNameAttribute = "SamAccountName", // SamAccountName or UserPrincipalName
        groupSearchBase = "ou=Departments,dc=dmit2015,dc=ca",
        bindDn = "cn=David Austin,ou=IT,ou=Departments,dc=dmit2015,dc=ca",
        bindDnPassword = "Password2015",
        priority = 5
)

@DatabaseIdentityStoreDefinition(
        dataSourceLookup="java:app/datasources/h2databaseDS",
        callerQuery="SELECT password FROM CallerUser WHERE username = ?",
        groupsQuery="SELECT groupname FROM CallerGroup WHERE username = ? ",
        priority = 10
)


@DataSourceDefinitions({
    @DataSourceDefinition(
            name="java:app/datasources/h2databaseDS",
            className="org.h2.jdbcx.JdbcDataSource",
//            url="jdbc:h2:file:~/databases/dmit2015-jaxrs-demodb",
            url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user="sa",
            password="sa"),
})

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}

