package com.ikolay.repository.entity;
import com.ikolay.repository.enums.ECommentType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private Long userId;
    @NotBlank(message = "Yorum alanı boş bırakılamaz !!")
    private String content;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ECommentType commentType = ECommentType.PENDING;

}
