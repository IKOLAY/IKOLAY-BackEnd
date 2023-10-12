package com.ikolay.dto.requests;

import com.ikolay.repository.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    @Builder.Default
    private Long employeeId=null;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate;
    @Positive(message = "İşlem değeri 0 ya da negatif olamaz!")
    private Double transactionAmount;
    private Boolean isPaid;
    @Enumerated(EnumType.STRING)
    private ETransactionType type;
    @Enumerated(EnumType.STRING)
    private EExpenseType expenseType;

    private Double currencyMultiplier;
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;
    @Enumerated(EnumType.STRING)
    private ETransactionStatus status;
    @Builder.Default
    private LocalDate confirmationDate=null;

    private String fileId;
}
