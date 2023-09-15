package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.mapper.IUserMapper;
import com.ikolay.repository.IUserRepository;
import com.ikolay.repository.entity.User;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Boolean register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new UserManagerException(ErrorType.EMAIL_EXIST);
        save(IUserMapper.INSTANCE.toUser(dto));
        return true;

    }
}
