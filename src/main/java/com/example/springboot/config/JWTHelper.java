package com.example.springboot.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.text.ParseException;
import java.util.Map;

public class JWTHelper {

    public static final String TOKEN_PREFIX = "bearer";
    public static final String REALMS = "realms/";
    public static final String ISSUER = "iss";

    public static String extractRealmFromHeader(String authHeader) throws ParseException {
        if (authHeader.toLowerCase().startsWith(TOKEN_PREFIX)) {
            DecodedJWT jwt = JWT.decode(authHeader.substring(TOKEN_PREFIX.length() + 1));
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get(ISSUER);
            String issuer = claim.asString();
            int realmIndex = issuer.indexOf(REALMS);
            if (realmIndex == -1) {
                throw new IllegalStateException("Not able to resolve realm from token!");
            }
            return issuer.substring(realmIndex + REALMS.length());
        } else {
            throw new ParseException("Invalid Auth Header. Missing Bearer prefix", 0);
        }
    }

}
