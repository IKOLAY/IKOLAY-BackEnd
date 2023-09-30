package com.ikolay.service;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.requests.AnnualProfitLossRequestDto;
import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ITransactionMapper;
import com.ikolay.repository.ITransactionRepository;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.enums.ETransactionType;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionService extends ServiceManager<FinancialTransaction, Long> {
    private final ITransactionRepository transactionRepository;
    private final CompanyService companyService;
    private final IUserManager userManager;


    public TransactionService(ITransactionRepository transactionRepository, CompanyService companyService, IUserManager userManager) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.companyService = companyService;
        this.userManager = userManager;
    }


    public FinancialTransaction add(AddTransactionRequestDto dto) {
        if (dto.getType().equals(ETransactionType.OUTCOME))
            dto.setTransactionAmount(dto.getTransactionAmount() * -1);
//        if (companyService.findById(dto.getCompanyId()).isEmpty())
//            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);   test için kapatıldı açılacak.
        return super.save(ITransactionMapper.INSTANCE.toFinancialTransaction(dto));
    }

    public List<FinancialTransaction> incomingPayments(Long companyId) {
        createMonthlyEmployeeSalary(companyId);
        return transactionRepository.findAllByTransactionDateBetweenAndIsPaidAndTypeAndCompanyId(LocalDate.now(), LocalDate.now().plusDays(15), false, ETransactionType.OUTCOME, companyId);
    }

    public List<AnnualProfitLossResponseDto> annualProfitLoss(AnnualProfitLossRequestDto dto) {
        return transactionRepository.annualProfitLoss(dto.getCompanyId(), dto.getStart(), dto.getEnd());
    }

    public List<AllExpensesResponseDto> findAllExpenses(Long companyId) {
        return transactionRepository.findAllExpenses(companyId);
    }

    public void createMonthlyEmployeeSalary(Long companyId) {
        LocalDate now = LocalDate.now();
        Long companysTotal = userManager.findTotalEmployeeSalary(companyId).getBody();
        if (companysTotal != null && companysTotal > 0) {
            companysTotal *= -1;
            if (now.getDayOfMonth() > 15) {
                createNextMonthsTotalSalaryPayment(companyId, companysTotal);
            } else {
                Optional<FinancialTransaction> transaction = transactionRepository.findByNameAndCompanyId("Toplam Personel Maaşı - " + now.getMonth() + "-" + now.getYear(), companyId);
                if (transaction.isEmpty()) {
                    save(FinancialTransaction.builder()
                            .name("Toplam Personel Maaşı - " + now.getMonth() + "-" + now.getYear())
                            .companyId(companyId)
                            .transactionAmount(companysTotal)
                            .transactionDate(LocalDate.of(now.getYear(), now.getMonth(), 15))
                            .isPaid(false)
                            .type(ETransactionType.OUTCOME)
                            .build());
                } else if (!transaction.get().getTransactionAmount().equals(companysTotal)) {
                    transaction.get().setTransactionAmount(companysTotal);
                    update(transaction.get());
                }
            }
        }
    }


    private void createNextMonthsTotalSalaryPayment(Long companyId, Long companysTotal) {
        LocalDate oneMonthLater = LocalDate.now().plusMonths(1);
        Optional<FinancialTransaction> transaction = transactionRepository.findByNameAndCompanyId("Toplam Personel Maaşı - " + oneMonthLater.getMonth() + "-" + oneMonthLater.getYear(), companyId);

        if (transaction.isEmpty()) {
            save(FinancialTransaction.builder()
                    .name("Toplam Personel Maaşı - " + oneMonthLater.getMonth() + "-" + oneMonthLater.getYear())
                    .companyId(companyId)
                    .transactionAmount(companysTotal)
                    .transactionDate(LocalDate.of(oneMonthLater.getYear(), oneMonthLater.getMonth(), 15))
                    .isPaid(false)
                    .type(ETransactionType.OUTCOME)
                    .build());
        } else if (!transaction.get().getTransactionAmount().equals(companysTotal)) {
            transaction.get().setTransactionAmount(companysTotal);
            update(transaction.get());
        }
    }
}
