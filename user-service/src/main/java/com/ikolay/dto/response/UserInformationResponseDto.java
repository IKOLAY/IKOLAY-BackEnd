package com.ikolay.dto.response;

import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class UserInformationResponseDto {
    private Long companyId;
    private String email;
    private String companyEmail;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
}
