package com.ikolay.mapper;

import com.ikolay.dto.requests.CommentAddDto;
import com.ikolay.dto.response.GetAllCommentsResponseDto;
import com.ikolay.repository.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    Comment toComment(final CommentAddDto dto);

    List<GetAllCommentsResponseDto> toGetAllCommentsResponseDto(final List<Comment> comment);
}
