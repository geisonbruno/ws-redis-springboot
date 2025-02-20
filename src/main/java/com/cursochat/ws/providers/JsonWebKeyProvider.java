package com.cursochat.ws.providers;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.auth0.jwk.UrlJwkProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;

@Component
public class JsonWebKeyProvider implements KeyProvider {

    private final UrlJwkProvider provider;

    public JsonWebKeyProvider(@Value("${app.auth.jwks-url}") final String jwksUrl) throws MalformedURLException {
        this.provider = new UrlJwkProvider(new URL(jwksUrl));
    }


    @Cacheable("public-keys")
    @Override
    public PublicKey getPublicKey(String keyId) {
        try {
            final Jwk jwk = provider.get(keyId);
            return jwk.getPublicKey();
        } catch (JwkException e) {
            throw new RuntimeException(e);
        }
    }
}
