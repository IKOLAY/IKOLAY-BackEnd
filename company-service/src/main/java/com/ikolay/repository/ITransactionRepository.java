package com.ikolay.repository;

import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.enums.ETransactionStatus;
import com.ikolay.repository.enums.ETransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    List<FinancialTransaction> findAllByTransactionDateBetweenAndIsPaidAndTypeAndCompanyIdAndStatus(LocalDate start, LocalDate end, Boolean isPaid, ETransactionType type, Long companyId, ETransactionStatus status);


    @Query("Select new com.ikolay.dto.response.AnnualProfitLossResponseDto(YEAR(f.transactionDate) as year, SUM(f.transactionAmount) as yearsTotal) from FinancialTransaction f where f.companyId = ?1 and f.isPaid=true and f.status='ACCEPTED' and f.transactionDate Between ?2 and ?3 group by year(f.transactionDate)")
    List<AnnualProfitLossResponseDto> annualProfitLoss(Long companyId,LocalDate start,LocalDate end);

    @Query("select new com.ikolay.dto.response.AllExpensesResponseDto(f.name as name, SUM(f.transactionAmount)) from FinancialTransaction f where f.companyId=?1 and f.type='OUTCOME' and f.isPaid=true and f.status='ACCEPTED' group by f.name")
    List<AllExpensesResponseDto> findAllExpenses(Long companyId);


    Optional<FinancialTransaction> findByNameAndCompanyId(String name,Long companyId);



}
