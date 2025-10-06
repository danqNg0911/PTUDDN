package com.example.ssoweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class TokenRefreshService {

    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Autowired
    private ClientRegistrationRepository registrationRepository;

    public OAuth2AccessToken refreshAccessToken(Authentication authentication) {
        String registrationId = "auth0";
        ClientRegistration clientRegistration = registrationRepository.findByRegistrationId(registrationId);
        OAuth2AuthorizedClient authorizedClient =
                clientService.loadAuthorizedClient(registrationId, authentication.getName());

        if (authorizedClient != null && authorizedClient.getRefreshToken() != null) {
            OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                    .refreshToken()
                    .build();

            OAuth2AuthorizationContext context = OAuth2AuthorizationContext
                    .withAuthorizedClient(authorizedClient)
                    .principal(authentication)
                    .build();

            OAuth2AuthorizedClient newClient = provider.authorize(context);

            if (newClient != null) {
                clientService.saveAuthorizedClient(newClient, authentication);
                return newClient.getAccessToken();
            }
        }
        return authorizedClient != null ? authorizedClient.getAccessToken() : null;
    }
}
