package com.ikolay.mapper;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICompanyMapper {
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);
    Company toCompany(final RegisterRequestDto dto);
}
