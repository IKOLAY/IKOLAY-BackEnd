package com.ikolay.mapper;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.repository.entity.FinancialTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITransactionMapper {
    ITransactionMapper INSTANCE = Mappers.getMapper(ITransactionMapper.class);

    FinancialTransaction toFinancialTransaction(final AddTransactionRequestDto dto);
}
