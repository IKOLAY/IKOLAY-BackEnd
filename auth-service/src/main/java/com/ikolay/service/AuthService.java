package com.ikolay.service;

import com.ikolay.dto.requests.ActivationRequestDto;
import com.ikolay.dto.requests.DoLoginRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.DoLoginResponseDto;
import com.ikolay.dto.response.RegisterResponseDto;
import com.ikolay.exception.AuthManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.ICompanyManager;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.IAuthMapper;
import com.ikolay.rabbitmq.model.MailModel;
import com.ikolay.rabbitmq.producer.MailProducer;
import com.ikolay.repository.IAuthRepository;
import com.ikolay.repository.entity.Auth;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import com.ikolay.utility.CodeGenerator;
import com.ikolay.utility.JwtTokenManager;
import com.ikolay.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final IAuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;
    private final IUserManager userManager;
    private final ICompanyManager companyManager;

    private final MailProducer mailProducer;

    public AuthService(IAuthRepository authRepository, JwtTokenManager jwtTokenManager, IUserManager userManager, ICompanyManager companyManager, MailProducer mailProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
        this.companyManager = companyManager;
        this.mailProducer = mailProducer;
    }

    //YORUMDAKİ COMPANY-MANAGER VE USER-MANAGER SATIRLARI HAZIRLANDIĞI ZAMAN YORUMDAN ÇIKARTILACAKTIR
    public RegisterResponseDto register(RegisterRequestDto dto) {
        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new AuthManagerException(ErrorType.EMAIL_EXIST);
        }
        switch (dto.getRole()) {
            case VISITOR:
                return saveVisitor(dto);
            case MANAGER:
                return saveManager(dto);
            case EMPLOYEE:
                return saveEmployee(dto);
            default:
                throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    @Transactional
    private RegisterResponseDto saveManager(RegisterRequestDto dto) {
        if (dto.getTaxNo() == null || dto.getCompanyName() == null)
            throw new AuthManagerException(ErrorType.COMPANY_NOT_CREATED);
        dto.setStatus(EStatus.PENDING);
        dto.setCompanyEmail(dto.getEmail());
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        dto.setAuthId(auth.getId());
        String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get();
        ResponseEntity<Long> companyId = companyManager.register(dto, token);
        dto.setCompanyId(companyId.getBody());
        userManager.register(dto, token);
        return RegisterResponseDto.builder()
                .message("Kayıdınız Admin Onayına Yollanmıştır")
                .build();
    }

    @Transactional
    private RegisterResponseDto saveVisitor(RegisterRequestDto dto) {
        String activationCode = CodeGenerator.genarateCode();
        dto.setStatus(EStatus.PENDING);
        dto.setCompanyEmail(dto.getEmail());
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(activationCode);
        save(auth);
        dto.setAuthId(auth.getId());
        String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get(); //security için eklendi ve kullanılacak.
        String mailToken = jwtTokenManager.createMailToken(auth.getId(), activationCode).get();
        userManager.register(dto, token);
        mailProducer.sendMail(MailModel.builder().email(dto.getEmail()).token(mailToken).role(dto.getRole()).build());
        return RegisterResponseDto.builder()
                .message("Email Onay Kodunuz Yollanmıştır")
                .build();
    }

    @Transactional
    private RegisterResponseDto saveEmployee(RegisterRequestDto dto) {
        dto.setStatus(EStatus.ACTIVE);
        String employeeEmail = dto.getEmail();
        String employeeGeneratedPassword = CodeGenerator.genarateCode();
        dto.setPassword(employeeGeneratedPassword);
        dto.setCompanyEmail(generateEmailAddressForEmployee(dto));
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get();
        dto.setAuthId(auth.getId());
        userManager.register(dto,token);
        mailProducer.sendMail(MailModel.builder().role(dto.getRole()).companyMail(dto.getCompanyEmail()).email(dto.getEmail()).password(employeeGeneratedPassword).build());
        return RegisterResponseDto.builder()
                .message("Personel Başarıyla Kaydedildi")
                .build();
    }

    private String generateEmailAddressForEmployee(RegisterRequestDto dto) {
        String companyName = companyManager.findByCompanyName(dto.getCompanyId()).getBody();
        List<Auth> auths = authRepository.findByFirstnameAndLastname(dto.getFirstname(), dto.getLastname());
        return auths.isEmpty() ? dto.getFirstname()+"."+ dto.getLastname() + "@" + companyName + ".com" : dto.getFirstname() +"."+ dto.getLastname() + auths.size() + "@" + companyName + ".com";

    }

    public DoLoginResponseDto doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByCompanyEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (auth.isEmpty()) throw new AuthManagerException(ErrorType.DOLOGIN_EMAILORPASSWORD_NOTEXISTS);
        statusCheck(auth.get().getStatus());
        String token = jwtTokenManager.createToken(auth.get().getId()).get();
        return DoLoginResponseDto.builder().token(token).role(auth.get().getRole().name()).build();
    }

    private void statusCheck(EStatus status){
        switch (status){
            case PENDING:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
            case INACTIVE:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE,"Hesabınız dondurulmuş lütfen firma yöneticiniz ile görüşün.");
            case BANNED:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE,"Hesabınız kalıcı olarak kapatılmıştır.");
            default:
                break;
        }
    }

    public RegisterResponseDto activation(String token) {
        Optional<ActivationRequestDto> fromToken = jwtTokenManager.getIdAndCodeFromToken(token);
        System.out.println("aktivasyon kodu:" + fromToken.get().getActivationCode());
        System.out.println(fromToken.get().getId());
        Optional<Auth> auth = authRepository.findOptionalByIdAndActivationCode(fromToken.get().getId(), fromToken.get().getActivationCode());
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        if (!auth.get().getStatus().equals(EStatus.PENDING))
            throw new AuthManagerException(ErrorType.INVALID_TOKEN, "Token daha önce kullanılmış.");
        auth.get().setStatus(EStatus.ACTIVE);
        userManager.confirmation(auth.get().getId());
        update(auth.get());
        //User service bağlantısı ile user service'inde güncellemesi yapılmalı.
        return RegisterResponseDto.builder().message("Hesabınız aktive edildi!").build();
    }

    public String confirmation(Boolean isAccepted,String content,String email,Long companyId){
        String message = null;
        Optional<Auth> auth = authRepository.findByEmail(email);
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);

        if(isAccepted){
            auth.get().setStatus(EStatus.ACTIVE);
            userManager.confirmation(auth.get().getId());
            update(auth.get());
            message= "Olumlu";
        } else {
            deleteById(auth.get().getId());
            companyManager.deleteById(companyId);
            userManager.deleteFromConfirmation(auth.get().getId());
            message = "Olumsuz";
        }
        mailProducer.sendMail(MailModel.builder().isAccepted(isAccepted).content(content).email(email).role(ERole.MANAGER).build());
        return message;
    }
}
