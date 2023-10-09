package com.ikolay.mapper;

import com.ikolay.dto.requests.CreateMembershipRequestDto;
import com.ikolay.repository.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMembershipMapper {
    IMembershipMapper INSTANCE = Mappers.getMapper(IMembershipMapper.class);

    Membership toMembership(final CreateMembershipRequestDto dto);

}
