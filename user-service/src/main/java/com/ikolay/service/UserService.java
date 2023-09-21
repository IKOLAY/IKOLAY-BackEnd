package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.UserInformationResponseDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.mapper.IUserMapper;
import com.ikolay.repository.IUserRepository;
import com.ikolay.repository.entity.User;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import com.ikolay.utility.JwtTokenManager;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository userRepository;
    private final JwtTokenManager tokenManager;

    public UserService(IUserRepository userRepository, JwtTokenManager tokenManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
    }

    public Boolean register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new UserManagerException(ErrorType.EMAIL_EXIST);
        User save = save(IUserMapper.INSTANCE.toUser(dto));
        System.out.println(save);
        return true;
    }

    public void deleteByAuthId(Long authId){
        Optional<User> user = userRepository.findByAuthId(authId);
        deleteById(user.get().getId());
    }

    public void confirmUser(Long authId) { //test için eklendi düzenlenmeli.
        Optional<User> user = userRepository.findByAuthId(authId);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        user.get().setStatus(EStatus.ACTIVE);
        update(user.get());
    }

    @PostConstruct
    private void addDefaultAdmin(){
        if (!userRepository.existsByEmail("admin@admin.com")){
            save(User.builder()
                    .email("admin@admin.com")
                    .password("admin")
                    .role(ERole.ADMIN)
                    .status(EStatus.ACTIVE)
                    .build());
        }
    }

    public UserInformationResponseDto getUserInformation(String token) {
         Long authId = tokenManager.getIdFromToken(token).get();
        Optional<User> user = userRepository.findByAuthId(authId);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Database'de User-Auth uyumsuzluğu mevcut.");
        return IUserMapper.INSTANCE.toUserInformationResponseDto(user.get());
    }
}
