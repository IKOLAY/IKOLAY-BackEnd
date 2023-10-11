package com.ikolay.mapper;

import com.ikolay.dto.requests.AdvancePaymentRequestDto;
import com.ikolay.dto.requests.CreateAdvanceRequestDto;
import com.ikolay.dto.response.EmployeeAdvanceAddResponseDto;
import com.ikolay.repository.entity.Advance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAdvanceMapper {
    IAdvanceMapper INSTANCE = Mappers.getMapper(IAdvanceMapper.class);

    Advance toAdvance(final CreateAdvanceRequestDto dto);

    @Mapping(target = "status",source = "advanceStatus")
    AdvancePaymentRequestDto toAdvancePaymentRequestDto(final Advance advance);

    EmployeeAdvanceAddResponseDto toEmployeeAdvanceAddResponseDto(final Advance advance);
}
