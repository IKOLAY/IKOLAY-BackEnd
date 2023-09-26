package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInformationResponseDto {
    private Long id;
    private Long authId;
    private Long companyId;
    private String email;
    private String companyEmail;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String photoUrl;
    private String salary;
    private Long shiftId;
}
