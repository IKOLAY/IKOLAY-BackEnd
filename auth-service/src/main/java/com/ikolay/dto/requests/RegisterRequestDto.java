package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @Email(message = "Uygun Bir Mail Adresi Giriniz !!")
    private String email;
    //VISITOR VE MANAGER'DA ISTENECEK EMPLOYEE'DE ISTENMICEK DEFAULT BIR DEĞER YOLLANACAK FRONTEND'DEN
    @NotBlank(message = "Password Boş Geçilemez")
    private String password;
    //FRONTEND'DEN OTOMATIK DOLUCAK
    private ERole role;
    @NotBlank(message = "Isim Boş Geçilemez")
    private String firstname;
    @NotBlank(message = "Soyisim Boş Geçilemez")
    private String lastname;
    //OTOMATIK DOLUCAK FRONTEND'DE
    @Builder.Default
    private EStatus status=EStatus.PENDING;

    //VISITOR VE EMPLOYEE'DEN ISTENMICEK SADECE MANAGER'DAN ISTENECEK
    private String companyName;
    private String taxNo;

    //DIŞARDAN ISTENMEYECEK BACKENDDE EKLENECEK
    private Long authId;
    private Long companyId;
}
