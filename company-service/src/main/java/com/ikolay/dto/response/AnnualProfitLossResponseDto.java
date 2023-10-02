package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnnualProfitLossResponseDto {
    Integer year;
    Double yearsTotal;
}
