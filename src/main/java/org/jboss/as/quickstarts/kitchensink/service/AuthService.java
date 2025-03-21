package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.dto.LoginRequestDTO;

public interface AuthService {
    String login(LoginRequestDTO loginRequestDTO);
}
