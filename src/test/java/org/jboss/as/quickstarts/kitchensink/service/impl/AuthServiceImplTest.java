package org.jboss.as.quickstarts.kitchensink.service.impl;

import org.jboss.as.quickstarts.kitchensink.config.JwtUtil;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.jboss.as.quickstarts.kitchensink.dto.LoginRequestDTO;
import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.jboss.as.quickstarts.kitchensink.exception.KitchenSinkException;
import org.jboss.as.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.as.quickstarts.kitchensink.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    private MemberEntity testMember;
    private LoginRequestDTO loginRequest;

    @BeforeEach
    void setUp() {
        testMember = new MemberEntity();
        testMember.setMemberId(1L);
        testMember.setPasswordHash("encodedPassword");
        testMember.setRole(List.of((MemberRoleEnum.USER)));

        loginRequest = new LoginRequestDTO();
        loginRequest.setMemberId(1L);
        loginRequest.setPassword("plainPassword");
    }

    @Test
    void login_Successful() {
        when(memberRepository.findByMemberId(1L)).thenReturn(Optional.of(testMember));
        when(passwordEncoder.matches("plainPassword", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(any(Map.class))).thenReturn("mockedJwtToken");

        String token = authService.login(loginRequest);

        assertNotNull(token);
        assertEquals("mockedJwtToken", token);

        verify(memberRepository, times(1)).findByMemberId(1L);
        verify(passwordEncoder, times(1)).matches("plainPassword", "encodedPassword");
        verify(jwtUtil, times(1)).generateToken(any(Map.class));
    }

    @Test
    void login_Failure_InvalidPassword() {
        when(memberRepository.findByMemberId(1L)).thenReturn(Optional.of(testMember));
        when(passwordEncoder.matches("plainPassword", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(KitchenSinkException.class, () -> authService.login(loginRequest));
        assertEquals("Login failed for user: 1", exception.getMessage());
    }

    @Test
    void login_Failure_UserNotFound() {
        when(memberRepository.findByMemberId(1L)).thenReturn(Optional.empty());
        assertThrows(MemberNotFoundException.class, () -> authService.login(loginRequest));
    }
}