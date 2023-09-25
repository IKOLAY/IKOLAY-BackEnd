package com.ikolay.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDto {
    private Long userId;
    private Long companyId;
    @NotBlank(message = "Yorum alanı boş bırakılamaz !!")
    private String content;
}
