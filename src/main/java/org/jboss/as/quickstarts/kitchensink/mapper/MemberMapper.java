package org.jboss.as.quickstarts.kitchensink.mapper;

import org.jboss.as.quickstarts.kitchensink.dto.MemberDTO;
import org.jboss.as.quickstarts.kitchensink.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    MemberDTO toDto(MemberEntity member);

    MemberEntity toEntity(MemberDTO member);

}

