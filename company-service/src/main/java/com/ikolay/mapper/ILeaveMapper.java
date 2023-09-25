package com.ikolay.mapper;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.repository.entity.Company;
import com.ikolay.repository.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ILeaveMapper {
    ILeaveMapper INSTANCE = Mappers.getMapper(ILeaveMapper.class);
    Leave toLeave(final CreateLeaveRequestDto dto);
}
