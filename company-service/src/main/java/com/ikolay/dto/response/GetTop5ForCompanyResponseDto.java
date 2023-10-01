package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTop5ForCompanyResponseDto {
    private Long id;
    private String logo;
    private String address;
    private String companyName;
    private String about;
    private String phone;
}
