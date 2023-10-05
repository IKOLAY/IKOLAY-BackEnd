package com.ikolay.service;

import com.ikolay.dto.requests.*;
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

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (dto.getTaxNo() == null || dto.getCompanyName() == null || dto.getCompanyName().equals("") || dto.getTaxNo().equals(""))
            throw new AuthManagerException(ErrorType.COMPANY_NOT_CREATED);
        dto.setStatus(EStatus.PENDING);
        dto.setCompanyEmail(dto.getEmail());
        dto.setCompanyName(dto.getCompanyName().toUpperCase());
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
        String employeeGeneratedPassword = CodeGenerator.genarateCode();
        dto.setPassword(employeeGeneratedPassword);
        dto.setCompanyEmail(generateEmailAddressForEmployee(dto));
        Auth auth = save(IAuthMapper.INSTANCE.toAuth(dto));
        String token = "Bearer " + jwtTokenManager.createToken(auth.getId()).get();
        dto.setAuthId(auth.getId());
        userManager.register(dto, token);
        mailProducer.sendMail(MailModel.builder().role(dto.getRole()).companyMail(dto.getCompanyEmail()).email(dto.getEmail()).password(employeeGeneratedPassword).build());
        return RegisterResponseDto.builder()
                .message("Personel Başarıyla Kaydedildi")
                .build();
    }

    private String generateEmailAddressForEmployee(RegisterRequestDto dto) {

        String multiName = Arrays.stream(dto.getFirstname().toLowerCase(Locale.ENGLISH).split(" ")).collect(Collectors.joining("."));
        String companyName = companyManager.findCompanyNameById(dto.getCompanyId()).getBody();
        if (companyName == null)
            throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Database Firma-Yönetim tutarsızlığı");
        companyName = companyName.split(" ")[0].toLowerCase();
        List<Auth> auths = authRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(dto.getFirstname(), dto.getLastname());
        return auths.isEmpty() ? multiName + "." + dto.getLastname().toLowerCase(Locale.ENGLISH) + "@" + companyName + ".com" : multiName + "." + dto.getLastname().toLowerCase(Locale.ENGLISH) + auths.size() + "@" + companyName + ".com";
        // return auths.isEmpty() ? dto.getFirstname().toLowerCase() + "." + dto.getLastname().toLowerCase() + "@ikolay.com" : dto.getFirstname().toLowerCase() + "." + dto.getLastname().toLowerCase() + auths.size() + "@ikolay.com";
    }

    public DoLoginResponseDto doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByCompanyEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (auth.isEmpty()) throw new AuthManagerException(ErrorType.DOLOGIN_EMAILORPASSWORD_NOTEXISTS);
        statusCheck(auth.get().getStatus());
        String token = jwtTokenManager.createToken(auth.get().getId()).get();
        return DoLoginResponseDto.builder().token(token).role(auth.get().getRole().name()).build();
    }

    private void statusCheck(EStatus status) {
        switch (status) {
            case PENDING:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
            case INACTIVE:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE, "Hesabınız dondurulmuş lütfen firma yöneticiniz ile görüşün.");
            case BANNED:
                throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE, "Hesabınız kalıcı olarak kapatılmıştır.");
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
        return RegisterResponseDto.builder().message("Hesabınız aktive edildi!").build();
    }

    public RegisterResponseDto confirmation(AdminApproveRequestDto dto) {
        RegisterResponseDto message = null;
        Optional<Auth> auth = authRepository.findByEmail(dto.getEmail());
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        if (!auth.get().getStatus().equals(EStatus.PENDING))
            throw new AuthManagerException(ErrorType.MANAGER_ALREADY_CONFIRMED);
        if (dto.getIsAccepted()) {
            auth.get().setStatus(EStatus.ACTIVE);
            userManager.confirmation(auth.get().getId());
            update(auth.get());
            message = RegisterResponseDto.builder().message("Olumlu").build();
        } else {
            deleteById(auth.get().getId());
            companyManager.deleteById(dto.getCompanyId());
            userManager.deleteFromConfirmation(auth.get().getId());
            message = RegisterResponseDto.builder().message("Olumsuz").build();
        }
        mailProducer.sendMail(MailModel.builder().isAccepted(dto.getIsAccepted()).content(dto.getContent()).email(dto.getEmail()).role(ERole.MANAGER).build());
        return message;
    }

    @PostConstruct
    private void addDefaultAdmin() {
        if (!authRepository.existsByEmail("admin@admin.com")) {
            save(Auth.builder()
                    .email("admin@admin.com")
                    .companyEmail("admin@admin.com")
                    .password("admin")
                    .role(ERole.ADMIN)
                    .status(EStatus.ACTIVE)
                    .build());
        }
        testDefaultEmployees();
    }

    private void testDefaultEmployees() {
        save(Auth.builder().email("doruk@gmail.com").firstname("Doruk").lastname("Tokinan").password("123").companyEmail("drk.drk@ikolay.com").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).build());
        save(Auth.builder().email("frkn@gmail.com").firstname("Furkan").lastname("Gülnihal").password("123").companyEmail("frk.frk@ikolay.com").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).build());
        save(Auth.builder().email("slm@gmail.com").firstname("Selim").lastname("Gülnihal").password("123").companyEmail("slm.slm@ikolay.com").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).build());
        save(Auth.builder().email("hly@gmail.com").firstname("Hülya").lastname("Martlı").password("123").companyEmail("hly.hly@ikolay.com").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).build());
        save(Auth.builder().email("aktas@gmail.com").firstname("Aktaş").lastname("Sabancı").password("123").companyEmail("akt.akt@ikolay.com").role(ERole.MANAGER).status(EStatus.ACTIVE).build());
        save(Auth.builder().email("emrsfa@gmail.com").firstname("Emre").lastname("Sefa").password("123").companyEmail("emrsfa@gmail.com").role(ERole.VISITOR).status(EStatus.ACTIVE).build());
    }

    public Boolean updateAuthInfo(UpdateUserRequestDto dto) {

        Optional<Auth> auth = findById(dto.getAuthId());
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER, "User-Auth serviceler arası uyumsuzluk mevcut.");
        auth.get().setEmail(dto.getEmail());
        auth.get().setFirstname(dto.getFirstname());
        auth.get().setLastname(dto.getLastname());
        update(auth.get());
        return true;

    }
    public Boolean deleteEmployee(Long id) {
        Optional<Auth> auth = authRepository.findById(id);
        if (auth.isEmpty())
            throw new AuthManagerException(ErrorType.BAD_REQUEST, "Lütfen girilen bilgilerin doğruluğunu kontrol edin.");
        deleteById(auth.get().getId());
        return true;
    }

}
