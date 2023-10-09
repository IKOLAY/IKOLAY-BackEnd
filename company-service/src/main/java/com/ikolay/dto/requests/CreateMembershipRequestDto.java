package com.ikolay.dto.requests;

import com.ikolay.repository.enums.ECurrencyType;
import com.ikolay.repository.enums.EMembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull(message = "Üyelik paketinin adı boş bırakılamaz.")
    private String name;
    @Positive(message = "Süre değeri negatif olamaz.")
    @Min(value = 0,message = "Süre değeri negatif olamaz.")
    private Long membershipDuration;
    @Positive(message = "Fiyat değeri negatif olamaz.")
    @Min(value = 0,message = "Fiyat değeri negatif olamaz.")
    private Double price;
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;
    @Builder.Default
    private Double currencyMultiplier=1d;

}
