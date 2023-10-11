package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class UpdateSalaryRequestDto {
    private Long id;
    @Positive(message = "Maaş 0 ya da negatif değer olamaz!")
    private Double salary;
}
