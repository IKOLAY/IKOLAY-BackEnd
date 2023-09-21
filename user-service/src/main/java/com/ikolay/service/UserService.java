package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.mapper.IUserMapper;
import com.ikolay.repository.IUserRepository;
import com.ikolay.repository.entity.User;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
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
                    .build());
        }
    }
    @PostConstruct
    private void testDefaultEmployees(){
        save(User.builder()
                .email("doruk@gmail.com")
                .firstname("drk")
                .lastname("drk")
                .phone("4124241")
                .role(ERole.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .companyId(1L)
                .build());
        save(User.builder()
                .email("frkn@gmail.com")
                .firstname("frk")
                .lastname("frk")
                .phone("412224241")
                .role(ERole.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .companyId(1L)
                .build());
        save(User.builder()
                .email("slm@gmail.com")
                .firstname("slm")
                .lastname("slm")
                .phone("412423341")
                .role(ERole.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .companyId(1L)
                .build());
        save(User.builder()
                .email("hly@gmail.com")
                .firstname("hly")
                .lastname("hly")
                .phone("4124241")
                .role(ERole.EMPLOYEE)
                .status(EStatus.ACTIVE)
                .companyId(1L)
                .build());
        save(User.builder()
                .email("aktas@gmail.com")
                .firstname("akt")
                .lastname("akt")
                .phone("4124241")
                .role(ERole.MANAGER)
                .companyId(1L)
                .status(EStatus.ACTIVE)
                .build());
        save(User.builder()
                .email("emrsfa@gmail.com")
                .firstname("emr")
                .lastname("emr")
                .phone("4124241")
                .role(ERole.VISITOR)
                .status(EStatus.ACTIVE)
                .build());
    }


    }
