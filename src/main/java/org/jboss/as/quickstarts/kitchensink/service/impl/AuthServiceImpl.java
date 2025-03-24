package org.jboss.as.quickstarts.kitchensink.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.as.quickstarts.kitchensink.exception.KitchenSinkException;
import org.jboss.as.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.as.quickstarts.kitchensink.config.JwtUtil;
import org.jboss.as.quickstarts.kitchensink.dto.LoginRequestDTO;
import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.jboss.as.quickstarts.kitchensink.repository.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String login(LoginRequestDTO loginRequestDTO){

        MemberEntity memberEntity = memberRepository.findByMemberId(loginRequestDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(loginRequestDTO.getMemberId()));
        if (passwordEncoder.matches(loginRequestDTO.getPassword(), memberEntity.getPasswordHash())){

            Map<String, String> jwtMap = new HashMap<>();
            jwtMap.put("sub", memberEntity.getMemberId().toString());
            jwtMap.put("role", memberEntity.getRole().stream().map(role -> role.name())
                    .collect(Collectors.joining(",")));

            return jwtUtil.generateToken(jwtMap);
        }
        else{
            throw new KitchenSinkException("Login failed for user: " + loginRequestDTO.getMemberId());
        }

    }




}
