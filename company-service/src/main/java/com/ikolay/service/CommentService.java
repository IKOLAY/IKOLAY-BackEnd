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
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
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

    public List<Comment> findAllPendingComments(){
        return commentRepository.findByCommentType(ECommentType.PENDING);
    }

    public List<GetAllCommentsResponseDto> getAllCommentsForGuest(Long companyId){
        List<Comment> comments = commentRepository.findCommentByCompany(companyId);
       // if (comments.isEmpty()) throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return ICommentMapper.INSTANCE.toGetAllCommentsResponseDto(comments);
    }

    @PostConstruct
    public void defaultComments(){

        save(Comment.builder().companyId(1l).commentType(ECommentType.PENDING).content("PENDING COMMENT").userId(2l).build());
        save(Comment.builder().companyId(1l).commentType(ECommentType.PENDING).content("PENDING COMMENT2").userId(3l).build());
        save(Comment.builder().companyId(1l).commentType(ECommentType.ACCEPTED).content("ACCEPTED COMMENT").userId(4l).build());
        save(Comment.builder().companyId(1l).commentType(ECommentType.REJECTED).content("REJECTED COMMENT").userId(5l).build());

    }

    public Boolean acceptComment(Long id) {
        Optional<Comment> comment = findById(id);
        if(comment.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen bilgide hata var.");
        comment.get().setCommentType(ECommentType.ACCEPTED);
        update(comment.get());
        return true;
    }

    public Boolean rejectComment(Long id) {
        Optional<Comment> comment = findById(id);
        if(comment.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen bilgide hata var.");
        comment.get().setCommentType(ECommentType.REJECTED);
        update(comment.get());
        return true;
    }

    public Comment findByUserId(Long userId) {
        Optional<Comment> comment = commentRepository.findByUserId(userId);
        if(comment.isEmpty())
            throw new CompanyManagerException(ErrorType.COMMENT_NOT_EXIST,"Henüz yorum yapmadınız!");
        return comment.get();
    }
}
