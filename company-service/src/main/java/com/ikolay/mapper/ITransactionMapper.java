package com.ikolay.mapper;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.response.FindMyExpensesResponseDto;
import com.ikolay.dto.response.GetCompanysPendingPaymentsResponseDto;
import com.ikolay.repository.entity.FinancialTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ITransactionMapper {
    ITransactionMapper INSTANCE = Mappers.getMapper(ITransactionMapper.class);

    FinancialTransaction toFinancialTransaction(final AddTransactionRequestDto dto);

    List<FindMyExpensesResponseDto> toFindMyExpensesResponseDtos(final List<FinancialTransaction> financialTransactions);

    List<GetCompanysPendingPaymentsResponseDto> toGetCompanysPendingPaymentsResponseDtos(final List<FinancialTransaction> financialTransactions);
}
