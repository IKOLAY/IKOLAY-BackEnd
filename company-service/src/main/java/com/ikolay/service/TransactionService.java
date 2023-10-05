package com.ikolay.service;

import com.ikolay.dto.requests.AddTransactionRequestDto;
import com.ikolay.dto.requests.AnnualProfitLossRequestDto;
import com.ikolay.dto.response.AllExpensesResponseDto;
import com.ikolay.dto.response.AnnualProfitLossResponseDto;
import com.ikolay.dto.response.FindMyExpensesResponseDto;
import com.ikolay.dto.response.GetCompanysPendingPaymentsResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ITransactionMapper;
import com.ikolay.repository.ITransactionRepository;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.enums.*;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
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
        dto.setTransactionAmount(dto.getCurrencyMultiplier()* dto.getTransactionAmount());
//        if (companyService.findById(dto.getCompanyId()).isEmpty())
//            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);   test için kapatıldı açılacak.
        return super.save(ITransactionMapper.INSTANCE.toFinancialTransaction(dto));
    }

    public List<FinancialTransaction> incomingPayments(Long companyId) {
        createMonthlyEmployeeSalary(companyId);
        return transactionRepository.findAllByTransactionDateBetweenAndIsPaidAndTypeAndCompanyIdAndStatus(LocalDate.now(), LocalDate.now().plusDays(15), false, ETransactionType.OUTCOME, companyId,ETransactionStatus.ACCEPTED);
    }

    public List<AnnualProfitLossResponseDto> annualProfitLoss(AnnualProfitLossRequestDto dto) {
        return transactionRepository.annualProfitLoss(dto.getCompanyId(), dto.getStart(), dto.getEnd());
    }

    public List<AllExpensesResponseDto> findAllExpenses(Long companyId) {
        return transactionRepository.findAllExpenses(companyId);
    }

    public void createMonthlyEmployeeSalary(Long companyId) {
        LocalDate now = LocalDate.now();
        Double companysTotal = userManager.findTotalEmployeeSalary(companyId).getBody();
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
                            .currencyType(ECurrencyType.TL)
                            .status(ETransactionStatus.ACCEPTED)
                            .confirmationDate(now)
                            .isPaid(false)
                            .expenseType(EExpenseType.MANAGER)
                            .type(ETransactionType.OUTCOME)
                            .build());
                } else if (!transaction.get().getTransactionAmount().equals(companysTotal)) {
                    transaction.get().setTransactionAmount(companysTotal);
                    update(transaction.get());
                }
            }
        }
    }


    private void createNextMonthsTotalSalaryPayment(Long companyId, Double companysTotal) {
        LocalDate oneMonthLater = LocalDate.now().plusMonths(1);
        Optional<FinancialTransaction> transaction = transactionRepository.findByNameAndCompanyId("Toplam Personel Maaşı - " + oneMonthLater.getMonth() + "-" + oneMonthLater.getYear(), companyId);

        if (transaction.isEmpty()) {
            save(FinancialTransaction.builder()
                    .name("Toplam Personel Maaşı - " + oneMonthLater.getMonth() + "-" + oneMonthLater.getYear())
                    .companyId(companyId)
                    .transactionAmount(companysTotal)
                    .transactionDate(LocalDate.of(oneMonthLater.getYear(), oneMonthLater.getMonth(), 15))
                    .currencyType(ECurrencyType.TL)
                    .status(ETransactionStatus.ACCEPTED)
                    .confirmationDate(LocalDate.now())
                    .isPaid(false)
                    .expenseType(EExpenseType.MANAGER)
                    .type(ETransactionType.OUTCOME)
                    .build());
        } else if (!transaction.get().getTransactionAmount().equals(companysTotal)) {
            transaction.get().setTransactionAmount(companysTotal);
            update(transaction.get());
        }
    }

    public List<String> getAllCurrencyList() {
        return Arrays.stream(ECurrencyType.values()).map(x -> x.name()).toList();
    }

    public List<String> getExpenseTypesForEmployee() {
        return Arrays.stream(EExpenseType.values()).filter(x->!x.name().equals("MANAGER")).map(y->y.name()).toList();
    }


    public List<FindMyExpensesResponseDto> findEmployeesExpenses(Long id) {
        return ITransactionMapper.INSTANCE.toFindMyExpensesResponseDtos(transactionRepository.findByEmployeeId(id));
    }

    public List<GetCompanysPendingPaymentsResponseDto> findByCompanyIdAndStatus(Long companyId, ETransactionStatus eTransactionStatus) {
        return ITransactionMapper.INSTANCE.toGetCompanysPendingPaymentsResponseDtos(transactionRepository.findByCompanyIdAndStatus(companyId,eTransactionStatus));
    }

    public Boolean confirmPayment(Long id) {
        Optional<FinancialTransaction> transaction = transactionRepository.findByIdAndStatus(id,ETransactionStatus.PENDING);
        if(transaction.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen veriyi kontrol edin!");
        transaction.get().setStatus(ETransactionStatus.ACCEPTED);
        transaction.get().setConfirmationDate(LocalDate.now());
        update(transaction.get());
        return true;
    }

    public Boolean rejectPayment(Long id) {
        Optional<FinancialTransaction> transaction = transactionRepository.findByIdAndStatus(id,ETransactionStatus.PENDING);
        if(transaction.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen veriyi kontrol edin!");
        transaction.get().setStatus(ETransactionStatus.REJECTED);
        transaction.get().setConfirmationDate(LocalDate.now());
        update(transaction.get());
        return true;
    }
}
