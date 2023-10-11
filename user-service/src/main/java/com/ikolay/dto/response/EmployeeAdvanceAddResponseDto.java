package com.ikolay.dto.response;

import com.ikolay.repository.enums.EAdvanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAdvanceAddResponseDto {
    private String systemMessage;
    private Long id;
    private Long companyId;
    private Long userId;
    private String description;
    private Double advanceAmount;
    private LocalDate confirmationDate;
    @Enumerated(EnumType.STRING)
    private EAdvanceStatus advanceStatus;
    private Long createDate;

}
