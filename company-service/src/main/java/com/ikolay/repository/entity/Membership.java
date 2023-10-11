package com.ikolay.repository.entity;



import com.ikolay.repository.enums.ECurrencyType;
import com.ikolay.repository.enums.EMembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Membership extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long membershipDuration;
    private Double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private EMembershipStatus status;
    @Builder.Default
    private Long activeUserCount=0L;
    @Enumerated(EnumType.STRING)
    private ECurrencyType currencyType;
    private Double currencyMultiplier;




}
