package com.ikolay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class FileResponse {
    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;

}
