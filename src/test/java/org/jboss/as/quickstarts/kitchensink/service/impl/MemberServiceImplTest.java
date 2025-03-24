package org.jboss.as.quickstarts.kitchensink.service.impl;

import org.jboss.as.quickstarts.kitchensink.constants.Constants;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.jboss.as.quickstarts.kitchensink.exception.KitchenSinkException;
import org.jboss.as.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.as.quickstarts.kitchensink.mapper.MemberMapper;
import org.jboss.as.quickstarts.kitchensink.repository.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.service.SequenceGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private SequenceGenerator sequenceGenerator;

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    private MemberEntity memberEntity;
    private MemberDTO memberDTO;

    @BeforeEach
    void setUp() {
        memberEntity = new MemberEntity();
        memberEntity.setMemberId(1L);
        memberEntity.setEmail("test@example.com");
        memberEntity.setPhoneNumber("1234567890");
        memberEntity.setRole(List.of(MemberRoleEnum.USER));

        memberDTO = new MemberDTO();
        memberDTO.setMemberId(1L);
        memberDTO.setEmail("test@example.com");
        memberDTO.setPhoneNumber("1234567890");
    }

    @Test
    void getAllMembers_ReturnsMemberList() {
        when(memberRepository.findAll()).thenReturn(List.of(memberEntity));
        when(memberMapper.toDto(memberEntity)).thenReturn(memberDTO);

        List<MemberDTO> result = memberService.getAllMembers();

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
    }

    @Test
    void getMemberByMemberId_ExistingMember_ReturnsMemberDTO() {
        when(memberRepository.findByMemberId(1L)).thenReturn(Optional.of(memberEntity));
        when(memberMapper.toDto(memberEntity)).thenReturn(memberDTO);

        MemberDTO result = memberService.getMemberByMemberId(1L);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getMemberByMemberId_NonExistingMember_ThrowsException() {
        when(memberRepository.findByMemberId(2L)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberByMemberId(2L));
    }

    @Test
    void addMember_DuplicateEmailOrPhone_ThrowsException() {
        when(memberRepository.findByEmailOrPhoneNumber(anyString(), anyString())).thenReturn(Optional.of(memberEntity));

        assertThrows(KitchenSinkException.class, () -> memberService.addMember(memberDTO));
    }

    @Test
    void addMember_SuccessfulAddition_ReturnsMemberDTO() {
        when(memberRepository.findByEmailOrPhoneNumber(anyString(), anyString())).thenReturn(Optional.empty());
        when(sequenceGenerator.generateSequence(Constants.MEMBERS_ID_SEQUENCE_NAME)).thenReturn(1L);
        memberDTO.setPassword("password");
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(memberMapper.toEntity(any(MemberDTO.class))).thenReturn(memberEntity);
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toDto(any(MemberEntity.class))).thenReturn(memberDTO);

        MemberDTO result = memberService.addMember(memberDTO);

        assertNotNull(result);
        assertEquals(1L, result.getMemberId());
    }

    @Test
    void updateMember_ExistingMember_UpdatesAndReturnsDTO() {
        when(memberRepository.findByMemberId(1L)).thenReturn(Optional.of(memberEntity));
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(memberEntity);
        when(memberMapper.toDto(any(MemberEntity.class))).thenReturn(memberDTO);

        MemberDTO result = memberService.updateMember(memberDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void updateMember_NonExistingMember_ThrowsException() {
        when(memberRepository.findByMemberId(any())).thenReturn(Optional.empty());
        assertThrows(MemberNotFoundException.class, () -> memberService.updateMember(memberDTO));
    }
}
