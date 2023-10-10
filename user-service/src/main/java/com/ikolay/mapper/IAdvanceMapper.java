package com.ikolay.mapper;

import com.ikolay.dto.requests.CreateAdvanceRequestDto;
import com.ikolay.repository.entity.Advance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAdvanceMapper {
    IAdvanceMapper INSTANCE = Mappers.getMapper(IAdvanceMapper.class);

    Advance toAdvance(final CreateAdvanceRequestDto dto);
}
