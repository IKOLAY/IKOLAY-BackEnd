package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvanceRequestDto {
    private Long userId;
    private Long companyId;
    private String description;
    @Positive(message = "İstenen avans değeri 0 ya da negatif olamaz!")
    private Double advanceAmount;
}
