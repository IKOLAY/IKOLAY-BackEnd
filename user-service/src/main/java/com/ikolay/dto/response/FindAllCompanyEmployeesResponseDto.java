package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class FindAllCompanyEmployeesResponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String photoUrl;
    private Double salary;
}
