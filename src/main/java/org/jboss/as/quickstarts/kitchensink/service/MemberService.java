package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService {

    List<MemberDTO> getAllMembers();

    // Get member by ID
    MemberDTO getMemberByMemberId(Long memberId);

    // Add new member
    MemberDTO addMember(MemberDTO memberResponseDTO);

    // Update existing member
    @Transactional
    MemberDTO updateMember( MemberDTO memberDTO);
}

