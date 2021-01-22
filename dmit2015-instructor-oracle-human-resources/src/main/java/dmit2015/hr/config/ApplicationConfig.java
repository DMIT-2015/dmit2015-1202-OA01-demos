package dmit2015.hr.config;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

@DataSourceDefinition(
    name = "java:app/datasources/oracleHrDS",
    className = "oracle.jdbc.pool.OracleDataSource",
    url = "jdbc:oracle:thin:@localhost:11521/xepdb1",
    user = "hr",
    password = "Password2015"
)

@ApplicationScoped  // This is a CDI managed bean that once it is created
                    // it stays in memory until the application shutdown
public class ApplicationConfig {
}
