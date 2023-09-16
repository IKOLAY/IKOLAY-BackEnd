package com.ikolay.rabbitmq.model;

import com.ikolay.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class MailModel implements Serializable {
    private String token; //authid ve activationCode var.
    private String email;
    private String companyMail;
    private String password;
    private ERole role;
    private Boolean isAccepted;
    private String content;
}
