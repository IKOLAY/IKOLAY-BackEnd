package com.ikolay.service;

import com.ikolay.dto.response.SaveFileResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.repository.FileRepository;
import com.ikolay.repository.entity.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;


    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public SaveFileResponseDto save(MultipartFile file) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setContentType(file.getContentType());
        try {
            fileEntity.setData(file.getBytes());
        } catch (IOException e) {
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Resim yükleme hatası.");
        }
        fileEntity.setSize(file.getSize());

        FileEntity save = fileRepository.save(fileEntity);
        return new SaveFileResponseDto(save.getId());
    }

    public Optional<FileEntity> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }
}
