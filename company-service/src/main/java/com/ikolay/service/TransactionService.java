package com.ikolay.service;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.requests.AnnualProfitLossRequestDto;
import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.mapper.ITransactionMapper;
import com.ikolay.repository.ITransactionRepository;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.enums.ETransactionType;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class TransactionService extends ServiceManager<FinancialTransaction,Long> {
    private final ITransactionRepository transactionRepository;
    private final CompanyService companyService;


    public TransactionService(ITransactionRepository transactionRepository, CompanyService companyService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.companyService = companyService;
    }


    public FinancialTransaction add(AddTransactionRequestDto dto) {
        if(dto.getType().equals(ETransactionType.OUTCOME))
            dto.setTransactionAmount(dto.getTransactionAmount()*-1);
//        if (companyService.findById(dto.getCompanyId()).isEmpty())
//            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);   test için kapatıldı açılacak.
        return super.save(ITransactionMapper.INSTANCE.toFinancialTransaction(dto));
    }

    public List<FinancialTransaction> incomingPayments(Long companyId){
        return transactionRepository.findAllByTransactionDateBetweenAndIsPaidAndTypeAndCompanyId(LocalDate.now(),LocalDate.now().plusDays(7),false, ETransactionType.OUTCOME,companyId);
    }

    public List<AnnualProfitLossResponseDto> annualProfitLoss(AnnualProfitLossRequestDto dto){
        return transactionRepository.annualProfitLoss(dto.getCompanyId(), dto.getStart(),dto.getEnd());
    }

    public List<AllExpensesResponseDto> findAllExpenses(Long companyId) {
        return transactionRepository.findAllExpenses(companyId);
    }
}
