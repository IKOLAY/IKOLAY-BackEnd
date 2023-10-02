package com.ikolay.repository.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILES")
public class FileEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

}
