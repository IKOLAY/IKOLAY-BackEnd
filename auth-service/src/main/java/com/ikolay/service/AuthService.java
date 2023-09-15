package com.ikolay.service;

import com.ikolay.dto.requests.DoLoginRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.DoLoginResponseDto;
import com.ikolay.dto.response.RegisterResponseDto;
import com.ikolay.exception.AuthManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.ICompanyManager;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.IAuthMapper;
import com.ikolay.repository.IAuthRepository;
import com.ikolay.repository.entity.Auth;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import com.ikolay.utility.CodeGenerator;
import com.ikolay.utility.JwtTokenManager;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IUserManager userManager;
    private final ICompanyManager companyManager;

    public AuthService(IAuthRepository authRepository, JwtTokenManager jwtTokenManager, IUserManager userManager, ICompanyManager companyManager) {
        super(authRepository);
        this.authRepository=authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
        this.companyManager = companyManager;
    }
//YORUMDAKİ COMPANY-MANAGER VE USER-MANAGER SATIRLARI HAZIRLANDIĞI ZAMAN YORUMDAN ÇIKARTILACAKTIR
    public RegisterResponseDto register(RegisterRequestDto dto){
        if (authRepository.existsByEmail(dto.getEmail())){
            throw new AuthManagerException(ErrorType.EMAIL_EXIST);
        }
        if (dto.getRole() == ERole.VISITOR){
            dto.setStatus(EStatus.PENDING);
            Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
            String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get();
            //MAIL YOLLANACAK BURADA
//            userManager.register(dto,token);
            return RegisterResponseDto.builder()
                    .message("Email Onay Kodunuz Yollanmıştır")
                    .build();
        } else if (dto.getRole() == ERole.MANAGER) {
            if (dto.getTaxNo() == null || dto.getCompanyName() == null) throw new AuthManagerException(ErrorType.COMPANY_NOT_CREATED);
            dto.setStatus(EStatus.PENDING);
            Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
            String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get();
//            ResponseEntity<Long> companyId = companyManager.register(dto, token);
//            dto.setCompanyId(companyId.getBody());
            //MAIL YOLLANACAK BURADA
//            companyManager.register(dto,token);
            System.out.println("manager " + token);
            return RegisterResponseDto.builder()
                    .message("Kayıdınız Admin Onayına Yollanmıştır")
                    .build();
        } else if (dto.getRole() == ERole.EMPLOYEE) {
            dto.setStatus(EStatus.ACTIVE);
            String employeeEmail = dto.getEmail();
            String employeeGeneratedPassword =  CodeGenerator.genarateCode();
            //MAIL YOLLANACAK BURADA
            dto.setPassword(employeeGeneratedPassword);
//            dto.setEmail(generateEmailAddressForEmployee(dto));
            Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));

            return RegisterResponseDto.builder()
                    .message("Personel Başarıyla Kaydedildi")
                    .build();
        } else{
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }

    }
    private String generateEmailAddressForEmployee(RegisterRequestDto dto){
        String companyName = companyManager.findByCompanyName(dto.getCompanyId()).getBody();
        return dto.getFirstname() + dto.getLastname() + "@" + companyName + ".com";
    }

    public DoLoginResponseDto doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (auth.isEmpty()) throw new AuthManagerException(ErrorType.DOLOGIN_EMAILORPASSWORD_NOTEXISTS);
        String token = jwtTokenManager.createToken(auth.get().getId()).get();
        return DoLoginResponseDto.builder().token(token).role(auth.get().getRole().name()).build();
    }
}
