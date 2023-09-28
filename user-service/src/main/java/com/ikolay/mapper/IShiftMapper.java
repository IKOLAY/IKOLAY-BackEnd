package com.ikolay.mapper;

import com.ikolay.dto.requests.CreateShiftRequestDto;
import com.ikolay.dto.response.FindShiftByCompanyIdResponseDto;
import com.ikolay.repository.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IShiftMapper {
    IShiftMapper INSTANCE = Mappers.getMapper(IShiftMapper.class);

    Shift toShift(final CreateShiftRequestDto dto);

    List<FindShiftByCompanyIdResponseDto> toFindShiftsByCompanyId(final List<Shift> shifts);

}
