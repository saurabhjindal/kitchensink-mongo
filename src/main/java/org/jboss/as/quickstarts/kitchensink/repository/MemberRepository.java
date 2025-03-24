package org.jboss.as.quickstarts.kitchensink.repository;

import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<MemberEntity, String> {

    Optional<MemberEntity> findByEmail(String email);  // find member by email

    Optional<MemberEntity> findByMemberId(Long memberId); //find member by member Id

    Optional<MemberEntity> findByEmailOrPhoneNumber(String email, String phoneNumber);

}
