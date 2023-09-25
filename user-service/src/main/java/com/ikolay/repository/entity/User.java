package com.ikolay.repository.entity;


import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
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
@Table(name = "tbl_user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private Long authId;
    @Column(unique = true)
    private String email;
    private String companyEmail;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status= EStatus.PENDING;

    private Long salary;

}
