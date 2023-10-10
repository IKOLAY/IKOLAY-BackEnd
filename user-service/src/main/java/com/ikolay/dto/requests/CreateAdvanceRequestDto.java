package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvanceRequestDto {
    private Long userId;
    private Long companyId;
    private String description;
    private Double advanceAmount;
}
