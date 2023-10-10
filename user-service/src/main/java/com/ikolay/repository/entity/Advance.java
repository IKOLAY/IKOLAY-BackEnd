package com.ikolay.repository.entity;

import com.ikolay.repository.enums.EAdvanceStatus;
import com.ikolay.repository.enums.ECurrencyType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advance extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private Long userId;
    private String description;
    private Double advanceAmount;
    @Enumerated(EnumType.STRING)
    private EAdvanceStatus advanceStatus;
}
