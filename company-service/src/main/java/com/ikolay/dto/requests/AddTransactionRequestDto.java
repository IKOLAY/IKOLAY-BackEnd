package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class AddTransactionRequestDto {
    @NotBlank(message = "Lütfen geçerli bir isim giriniz.")
    private String name;
    @NotNull(message = "Sistem hatası lütfen admin ile görüşün.")
    private Long companyId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    private Long transactionAmount;
    private Boolean isPaid;
    @Enumerated(EnumType.STRING)
    private ETransactionType type;
}
