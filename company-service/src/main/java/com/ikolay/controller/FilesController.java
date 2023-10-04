package com.ikolay.controller;

import com.ikolay.dto.response.FileResponse;
import com.ikolay.dto.response.SaveFileResponseDto;
import com.ikolay.repository.entity.FileEntity;
import com.ikolay.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/files")
public class FilesController {
    private final FileService fileService;


    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<SaveFileResponseDto> upload(@RequestParam("file") MultipartFile file) {
            return ResponseEntity.ok(fileService.save(file));
    }

    @GetMapping
    public List<FileResponse> list() {
        return fileService.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(FileEntity fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/")
                .path(fileEntity.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        FileEntity fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

       fileService.delete(fileEntityOptional.get());
        return ResponseEntity.ok().build();
    }
}
