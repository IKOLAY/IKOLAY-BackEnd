package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @Email(message = "Uygun Bir Mail Adresi Giriniz !!")
    private String email;
    @NotBlank(message = "Password Boş Geçilemez")
    private String password;
    private ERole role;
    @NotBlank(message = "Isim Boş Geçilemez")
    private String firstname;
    @NotBlank(message = "Soyisim Boş Geçilemez")
    private String lastname;
    @Builder.Default
    private EStatus status=EStatus.PENDING;

    private String companyName;
    private String taxNo;

    private Long membershipId;
}
