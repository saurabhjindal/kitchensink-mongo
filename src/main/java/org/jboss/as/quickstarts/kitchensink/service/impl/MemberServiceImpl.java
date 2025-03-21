package org.jboss.as.quickstarts.kitchensink.service.impl;

import org.jboss.as.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.as.quickstarts.kitchensink.constants.Constants;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.jboss.as.quickstarts.kitchensink.mapper.MemberMapper;
import org.jboss.as.quickstarts.kitchensink.repository.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.service.MemberService;
import org.jboss.as.quickstarts.kitchensink.service.SequenceGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SequenceGenerator sequenceGenerator;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl (MemberRepository memberRepository,SequenceGenerator sequenceGenerator,
    MemberMapper memberMapper, PasswordEncoder passwordEncoder){
        this.sequenceGenerator = sequenceGenerator;
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all members
    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(memberMapper::toDto).collect(Collectors.toList());
    }

    // Get member by ID
    @Override
    public MemberDTO getMemberByMemberId(Long memberId) {
        MemberEntity memberEntity = getMemberEntity(memberId);
        return memberMapper.toDto(memberEntity);
    }

    // Add new member
    @Override
    @Transactional
    public MemberDTO addMember(MemberDTO memberResponseDTO) {
        MemberEntity memberEntity = memberMapper.toEntity(memberResponseDTO);
        memberEntity.setMemberId(sequenceGenerator.generateSequence(Constants.MEMBERS_ID_SEQUENCE_NAME));
        memberEntity.setPasswordHash(passwordEncoder.encode(memberResponseDTO.getPassword()));
        memberEntity.setRole(List.of(MemberRoleEnum.USER));
        return memberMapper.toDto(memberRepository.save(memberEntity)) ;
    }


    // Update existing member
    @Transactional
    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) {

        MemberEntity memberEntity = getMemberEntity(memberDTO.getMemberId());
        memberEntity.setPhoneNumber(memberDTO.getPhoneNumber());
        memberEntity.setEmail(memberDTO.getEmail());
        return memberMapper.toDto(memberRepository.save(memberEntity));
    }

//    // Delete member by ID
//    public void deleteMember(String id) {
//        memberRepository.deleteById(id);
//    }

    private MemberEntity getMemberEntity(Long memberId) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId)
                .orElseThrow(() ->
                        new  MemberNotFoundException("member Id : " + memberId + "Not found.",
                                "Not able to find specified member id."
                        ));
        return memberEntity;
    }


}


