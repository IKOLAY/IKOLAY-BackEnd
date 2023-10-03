package com.ikolay.dto.response;

import com.ikolay.repository.enums.ECurrencyType;
import com.ikolay.repository.enums.EExpenseType;
import com.ikolay.repository.enums.ETransactionStatus;
import com.ikolay.repository.enums.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class FindMyExpensesResponseDto {

    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    private LocalDate confirmationDate;
    private Double transactionAmount;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType = ECurrencyType.TL;
    @Enumerated(EnumType.STRING)
    private ETransactionStatus status;
    @Builder.Default
    private Double currencyMultiplier = 1d;
    @Enumerated(EnumType.STRING)
    private EExpenseType expenseType;
    private String fileId;
}
