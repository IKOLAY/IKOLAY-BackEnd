package com.ikolay.mapper;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.AllConfirmationInfoResponseDto;
import com.ikolay.dto.response.FindAllCompanyEmployeesResponseDto;
import com.ikolay.dto.response.UpdateUserResponseDto;
import com.ikolay.dto.response.UserInformationResponseDto;
import com.ikolay.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User toUser(final RegisterRequestDto dto);

    UserInformationResponseDto toUserInformationResponseDto(final User user);

    List<FindAllCompanyEmployeesResponseDto> toListFindAllCompanyEmployeesResponseDto(final List<User> user);

    AllConfirmationInfoResponseDto toAllConfirmationResponseDto(final User user);

    UpdateUserResponseDto toUpdateUserResponseDto(final User user);

}
