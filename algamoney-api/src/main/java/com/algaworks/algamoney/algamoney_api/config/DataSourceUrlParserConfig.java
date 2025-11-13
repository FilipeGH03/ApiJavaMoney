package com.algaworks.algamoney.algamoney_api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Parses a JDBC URL that contains embedded credentials like
 * jdbc:postgresql://user:pass@host:port/db and converts it into
 * a proper JDBC URL plus separate username/password, so the
 * PostgreSQL driver accepts it.
 *
 * This is useful when a platform (like Render) provides a single
 * connection string including credentials in that form.
 */
@Configuration
@Profile("prod")
public class DataSourceUrlParserConfig {

    @Bean
    @Primary
    public DataSource dataSource(Environment env, DataSourceProperties props) {
        // Prefer environment variable (exact name) if present, otherwise use configured url
        String rawUrl = env.getProperty("SPRING_DATASOURCE_URL");
        if (rawUrl == null) {
            rawUrl = props.getUrl();
        }

        if (rawUrl != null && rawUrl.startsWith("jdbc:postgresql://") && rawUrl.contains("@")) {
            // Handle strings like: jdbc:postgresql://user:pass@host:port/database
            try {
                String withoutPrefix = rawUrl.substring("jdbc:postgresql://".length());
                int at = withoutPrefix.indexOf('@');
                if (at > 0) {
                    String credentials = withoutPrefix.substring(0, at);
                    String hostAndDb = withoutPrefix.substring(at + 1);
                    String[] credParts = credentials.split(":", 2);
                    if (credParts.length == 2) {
                        String user = credParts[0];
                        String pass = credParts[1];
                        String newUrl = "jdbc:postgresql://" + hostAndDb;
                        props.setUrl(newUrl);
                        // Only override username/password if not explicitly set elsewhere
                        if (props.getUsername() == null || props.getUsername().isEmpty()) {
                            props.setUsername(user);
                        }
                        if (props.getPassword() == null || props.getPassword().isEmpty()) {
                            props.setPassword(pass);
                        }
                    }
                }
            } catch (Exception ex) {
                // If parsing fails, fall back to the provided props as-is; do not crash here.
                // The original exception would surface during bean creation otherwise.
            }
        }

        return props.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}
