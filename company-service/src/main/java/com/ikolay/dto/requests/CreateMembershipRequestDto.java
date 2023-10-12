package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ECurrencyType;
import com.ikolay.repository.enums.EMembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class CreateMembershipRequestDto {

    @NotBlank(message = "Üyelik paketinin adı boş bırakılamaz.")
    private String name;
    @Positive(message = "Süre değeri 0 ya da negatif olamaz.")
    @Min(value = 0,message = "Süre değeri 0 ya da negatif olamaz.")
    private Long membershipDuration;
    @Min(value = 0,message = "Fiyat değeri negatif olamaz.")
    private Double price;
    @Length(max = 200, message = "Maksimum açıklama uzunluğu 200 karakterdir.")
    private String description;
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;
    @Builder.Default
    @Positive(message = "Negatif ve 0'dan küçük değer girilemez!!")
    private Double currencyMultiplier=1d;

}
