package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateLeaveRequestDto {
    @NotBlank(message = "Lütfen tatil adını giriniz!")
    @Size(min = 4 ,message = "En kısa tatil adı 4 karakter olabilir!")
    private String leaveName;
    @NotNull(message = "Başlangıç tarihi olmadan tatil kaydı yapılamaz!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startingDate;
    @NotNull(message = "Lütfen geçerli bir tatil süresi giriniz!")
    private Integer duration;
    private String email;
    private Long userId;
    private Long companyId;
}
