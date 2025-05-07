package com.manu.adminpannel.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.manu.adminpannel.entity.User;
import com.manu.adminpannel.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (userRepository == null) {
            userRepository = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(request.getServletContext())
                    .getBean(UserRepository.class);
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        String provider;
        if (oauthUser.getAttribute("login") != null) {
            provider = "GITHUB";
        } else {
            provider = "GOOGLE";
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            long userCount = userRepository.count();

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setProvider(provider);
            user.setRole(userCount == 0 ? "ADMIN" : "USER"); 
            userRepository.save(user);
        }

        response.sendRedirect("/user/me");
    }
}
