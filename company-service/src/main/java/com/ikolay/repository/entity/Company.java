package com.ikolay.repository.entity;


import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String logo;
    private String address;
    private String about;
    private String companyName;
    @Column(unique = true)
    private String taxNo;

    private Long membershipId;
    private LocalDate membershipStarted;
    private LocalDate membershipExpiration;

}
