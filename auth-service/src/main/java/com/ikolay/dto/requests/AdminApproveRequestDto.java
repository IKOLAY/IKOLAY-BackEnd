package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminApproveRequestDto {
    @Builder.Default
    Boolean isAccepted = false;

    @Builder.Default
    String content="Üzgünüz";
    String email;
    Long companyId;
}
