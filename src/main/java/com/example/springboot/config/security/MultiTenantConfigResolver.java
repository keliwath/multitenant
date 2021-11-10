package com.example.springboot.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springboot.config.JWTHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MultiTenantConfigResolver extends KeycloakSpringBootConfigResolver implements KeycloakConfigResolver {

    public static final String AUTH_HEADER = "Authorization";

    private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();

    @Setter
    private static AdapterConfig adapterConfig;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {

        String realm;

        try {
            String authHeader = request.getHeader(AUTH_HEADER);
            if (authHeader == null) {
                log.warn("No Auth header provided");
                return emptyDeployment();
            }
            realm = JWTHelper.extractRealmFromHeader(authHeader);
        } catch (ParseException ex) {
            throw new IllegalStateException("Invalid JWT provided");
        }

        log.info("Request from {}", realm);

        KeycloakDeployment deployment = cache.get(realm);
        if (null == deployment) {
            // not found on the simple cache, try to load it from the file system
            InputStream is = getClass().getResourceAsStream("/" + realm + "-keycloak.json");
            if (is == null) {
                throw new IllegalStateException("Not able to find the file /" + realm + "-keycloak.json");
            }
            deployment = KeycloakDeploymentBuilder.build(is);
            cache.put(realm, deployment);
        }

        return deployment;
    }

    private KeycloakDeployment emptyDeployment() {
        KeycloakDeployment kd = new KeycloakDeployment();
        kd.setBearerOnly(true);
        return kd;
    }


}