package com.ikolay.repository;

import com.ikolay.repository.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,String> {
}
