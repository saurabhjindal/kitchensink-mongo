package org.jboss.as.quickstarts.kitchensink.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.jboss.as.quickstarts.kitchensink.exception.KitchenSinkException;
import org.jboss.as.quickstarts.kitchensink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    // ✅ Get all members
    @GetMapping("/all")
    @Operation(summary = "Get all members", description = "Retrieve a list of all members.")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        log.info("Get all members");
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // ✅ Get a single member by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Retrieve a specific member by their ID.")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id , @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Get member by ID {}", id);
        validateId(id, userDetails);
        return ResponseEntity.ok(memberService.getMemberByMemberId(id));
    }

    // ✅ Add a new member
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new member", description = "Create a new member in the system.")
    public ResponseEntity<MemberDTO> addMember(
            @RequestBody
            @Valid MemberDTO member) {
        log.info("Add member {}", member);
        return ResponseEntity.ok(memberService.addMember(member));
    }

    // ✅ Update an existing member
    @PutMapping
    @Operation(summary = "Update a member", description = "Update the details of an existing member.")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody @Valid MemberDTO memberDTO, @AuthenticationPrincipal UserDetails userDetails ) {

        log.info("Update member {}", memberDTO);
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + MemberRoleEnum.ADMIN.name()))) {
            validateId(memberDTO.getMemberId(), userDetails);
        }

        return ResponseEntity.ok(memberService.updateMember(memberDTO));
    }

//    // ✅ Delete a member
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete a member", description = "Remove a member from the system.")
//    public ResponseEntity<Void> deleteMember(@PathVariable long id) {
//        if (memberService.deleteMember(id)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    private void validateId(Long id, UserDetails userDetails) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        if (!id.equals(memberId))
            throw new KitchenSinkException("Member ID mismatch");
    }
}

