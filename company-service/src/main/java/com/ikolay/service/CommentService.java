package com.ikolay.service;

import com.ikolay.dto.requests.CommentAddDto;
import com.ikolay.dto.response.GetAllCommentsResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ICommentMapper;
import com.ikolay.repository.ICommentRepository;
import com.ikolay.repository.entity.Comment;
import com.ikolay.repository.enums.ECommentType;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment,Long> {
    private final ICommentRepository commentRepository;
    private final IUserManager userManager;
    public CommentService(ICommentRepository commentRepository, IUserManager userManager) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.userManager = userManager;
    }

    public Comment commentAddForAdmin(CommentAddDto dto){
        if (dto.getCompanyId() == null) throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        if (dto.getUserId() == null) throw new CompanyManagerException(ErrorType.USER_NOT_CREATED,"DUZENLE !!");
        Optional<Comment> comment = commentRepository.findByCompanyIdAndUserId(dto.getCompanyId(), dto.getUserId());
        if (comment.isPresent()){
            if (comment.get().getCommentType() == ECommentType.PENDING) throw new CompanyManagerException(ErrorType.COMMENT_NOT_CREATED,"Önceki yorumunuz henüz sonuçlandırılmamıştır !! Yorumu güncellemek için sonuçlanmasını bekleyiniz !! ");
            Comment inputComment = ICommentMapper.INSTANCE.toComment(dto);
            inputComment.setCreateDate(comment.get().getCreateDate());
            inputComment.setId(comment.get().getId());
            return update(inputComment);
        }
        return save(ICommentMapper.INSTANCE.toComment(dto));
    }

    public List<Comment> findCommentByCompanyId(Long companyId){
        if (commentRepository.findCommentByCompanyId(companyId).isEmpty()) throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return commentRepository.findCommentByCompanyId(companyId);
    }

    public List<GetAllCommentsResponseDto> getAllCommentsForGuest(Long companyId){
        if (commentRepository.findCommentByCompany(companyId).isEmpty()) throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        List<Comment> commentByCompany = commentRepository.findCommentByCompany(companyId);
        return ICommentMapper.INSTANCE.toGetAllCommentsResponseDto(commentByCompany);

    }

}
