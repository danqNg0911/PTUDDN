package com.example.ssoweb.controller;

import com.example.ssoweb.service.TokenRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    private TokenRefreshService tokenRefreshService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser,
        @RegisteredOAuth2AuthorizedClient("auth0") OAuth2AuthorizedClient authorizedClient) {

        if (oidcUser != null) {
            model.addAttribute("name", oidcUser.getFullName());
            model.addAttribute("email", oidcUser.getEmail());
        }

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String refreshToken = authorizedClient.getRefreshToken() != null
                ? authorizedClient.getRefreshToken().getTokenValue()
                : "No refresh token";

        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);

        return "home";
    }

    @GetMapping("/refresh")
    @ResponseBody
    public String refreshToken(@AuthenticationPrincipal OidcUser oidcUser, Authentication authentication) {
        OAuth2AccessToken newToken = tokenRefreshService.refreshAccessToken(authentication);
        return newToken != null ? newToken.getTokenValue() : "Không thể làm mới token";
    }

}
