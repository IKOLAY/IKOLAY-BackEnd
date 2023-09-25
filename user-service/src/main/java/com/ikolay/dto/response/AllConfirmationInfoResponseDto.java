package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class AllConfirmationInfoResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private Long companyId;
    private String companyName;
    private String taxNo;
}
