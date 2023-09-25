package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.FindAllCompanyEmployeesResponseDto;
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
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, Long> {

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

    public void deleteByAuthId(Long authId) {
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
    private void addDefaultAdmin() {
        if (!userRepository.existsByEmail("admin@admin.com")) {
            save(User.builder()
                    .email("admin@admin.com")
                    .companyEmail("admin@admin.com")
                    .authId(1L)
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
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Database'de User-Auth uyumsuzluğu mevcut.");
        return IUserMapper.INSTANCE.toUserInformationResponseDto(user.get());
    }
    @PostConstruct
    private void testDefaultEmployees() {
        save(User.builder().authId(2L).email("doruk@gmail.com").firstname("drk").lastname("drk").password("123").companyEmail("drk.drk@ikolay.com").phone("4124241").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).build());
        save(User.builder().authId(3L).email("frkn@gmail.com").firstname("frk").lastname("frk").password("123").companyEmail("frk.frk@ikolay.com").phone("412224241").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).build());
        save(User.builder().authId(4L).email("slm@gmail.com").firstname("slm").lastname("slm").password("123").companyEmail("slm.slm@ikolay.com").phone("412423341").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).build());
        save(User.builder().authId(5L).email("hly@gmail.com").firstname("hly").lastname("hly").password("123").companyEmail("hly.hly@ikolay.com").phone("4124241").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).build());
        save(User.builder().authId(6L).email("aktas@gmail.com").firstname("akt").lastname("akt").password("123").companyEmail("akt.akt@ikolay.com").phone("4124241").role(ERole.MANAGER).companyId(1L).status(EStatus.ACTIVE).build());
        save(User.builder().authId(7L).email("emrsfa@gmail.com").firstname("emr").lastname("emr").password("123").companyEmail("emrsfa@gmail.com").phone("4124241").role(ERole.VISITOR).status(EStatus.ACTIVE).build());
    }
    public List<FindAllCompanyEmployeesResponseDto> personelList(Long companyId) {
        return IUserMapper.INSTANCE.toListFindAllCompanyEmployeesResponseDto(userRepository.findByCompanyIdAndRole(companyId,ERole.EMPLOYEE));
    }

    public Long findIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        return user.get().getId();
    }

}

