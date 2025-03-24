package org.jboss.as.quickstarts.kitchensink.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.as.quickstarts.kitchensink.dto.LoginRequestDTO;
import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.jboss.as.quickstarts.kitchensink.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    // ✅ Add a new member
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login for a member using memberId", description = "Login for a member using memberId")
    public ResponseEntity<String> login(
            @RequestBody
            @Valid LoginRequestDTO loginRequestDTO) {
        log.info("Login request : {}", loginRequestDTO);
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }


}
