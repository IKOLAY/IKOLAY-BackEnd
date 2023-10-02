package com.ikolay.repository.entity;

import com.ikolay.repository.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
public class FinancialTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long companyId;
    private Long employeeId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    private LocalDate confirmationDate;
    private Double transactionAmount;
    private Boolean isPaid;
    @Enumerated(EnumType.STRING) //Gelir-Gider bilgisi
    private ETransactionType type;
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
