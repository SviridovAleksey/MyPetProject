package com.sviridov.auth.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.server")
@Data
@NoArgsConstructor
public class KeycloakServerProperties {
    String contextPath = "/auth";
    String realmImportFile = "sviridov-realm.json";
    AdminUser adminUser = new AdminUser();

    @Data
    public static class AdminUser {
        String username = "admin";
        String password = "admin";
    }
}
