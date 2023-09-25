package com.ikolay.repository;

import com.ikolay.repository.entity.Comment;
import com.ikolay.repository.enums.ECommentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByCompanyId(Long companyId);

    Optional<Comment> findByUserId(Long id);

    Optional<Comment> findByCompanyIdAndUserId(Long companyId,Long userId);

    @Query("select c from Comment c where c.companyId=?1 and c.commentType='ACCEPTED'")
    List<Comment> findCommentByCompany(Long companyId);


}
