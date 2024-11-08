package org.tricode.hackathonserverside.util;

import org.springframework.security.core.Authentication;
import org.tricode.hackathonserverside.model.User;

public class AuthenticationUtil {
    public static User getAuthenticatedUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
