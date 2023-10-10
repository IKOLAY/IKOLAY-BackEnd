package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ETransactionStatus;
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
public class AdvancePaymentRequestDto {

    private String name;
    private Long companyId;
    private Long employeeId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    private Double transactionAmount;
    private LocalDate confirmationDate;
    @Enumerated(EnumType.STRING)
    private ETransactionStatus status;


}
