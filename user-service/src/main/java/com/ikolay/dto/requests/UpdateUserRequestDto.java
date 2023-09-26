package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class UpdateUserRequestDto {
    @NotNull(message = "Önyüzden gelen bilgiyi kontrol edin...")
    Long id;

    @NotBlank(message = "Soyisim boş bırakılamaz.")
    private String firstname;

    @NotBlank(message = "Soyisim boş bırakılamaz.")
    private String lastname;

    @Email(message = "Lütfen geçerli bir email adresi girin.")
    private String email;

    private String phone;
    private String address;
    private String photoUrl;
    private Long authId;

}
