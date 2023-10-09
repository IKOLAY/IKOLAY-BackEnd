package com.ikolay.service;

import com.ikolay.dto.requests.*;
import com.ikolay.dto.response.*;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.manager.IAuthManager;
import com.ikolay.manager.ICompanyManager;
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
    private final ICompanyManager companyManager;
    private final ShiftService shiftService;

    private final IAuthManager authManager;

    public UserService(IUserRepository userRepository, JwtTokenManager tokenManager, ICompanyManager companyManager, ShiftService shiftService, IAuthManager authManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
        this.companyManager = companyManager;
        this.shiftService = shiftService;
        this.authManager = authManager;
    }

    public Boolean register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new UserManagerException(ErrorType.EMAIL_EXIST);
        save(IUserMapper.INSTANCE.toUser(dto));
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
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@admin.com")
                    .companyEmail("admin@admin.com")
                    .authId(1L)
                    .password("admin")
                    .role(ERole.ADMIN)
                    .status(EStatus.ACTIVE)
                    .photoUrl("https://cdn-icons-png.flaticon.com/512/2304/2304226.png")
                    .build());
            testDefaultEmployees();
        }

    }

    public UserInformationResponseDto getUserInformation(String token) {
        Long authId = tokenManager.getIdFromToken(token).get();
        Optional<User> user = userRepository.findByAuthId(authId);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Database'de User-Auth uyumsuzluğu mevcut.");
        return IUserMapper.INSTANCE.toUserInformationResponseDto(user.get());
    }
    private void testDefaultEmployees() {
        save(User.builder().authId(2L).salary(30000d).email("doruk@gmail.com").shiftId(1L).firstname("Doruk").lastname("Tokinan").address("Mumtaz Zeytinoglu Bulvari, No. 13 Eskişehir Ankara").password("123").companyEmail("drk.drk@ikolay.com").phone("5329701501").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/2.webp").build());
        save(User.builder().authId(3L).salary(40000d).email("frkn@gmail.com").shiftId(2L).firstname("Furkan").lastname("Gülnihal").address("Selahaddin Eyyubi Mh. 1538 Sk. No: 43 - 45 Esenyurt İstanbul").password("123").companyEmail("frk.frk@ikolay.com").phone("5329736481").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/1.webp").companyId(1L).build());
        save(User.builder().authId(4L).salary(50000d).email("slm@gmail.com").shiftId(3L).firstname("Selim").lastname("Gülnihal").address("15 Temmuz Mah. Bahar Cad. No 37 Bağcılar İstanbul").password("123").companyEmail("slm.slm@ikolay.com").phone("5329599979").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/8.webp").build());
        save(User.builder().authId(5L).salary(60000d).email("hly@gmail.com").shiftId(1L).firstname("Hülya").lastname("Martlı").address("Celtikler Mah. Soganlik Sok. No:45 Geyve Sakarya").password("123").companyEmail("hly.hly@ikolay.com").phone("5323262590").role(ERole.EMPLOYEE).status(EStatus.ACTIVE).companyId(1L).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/4.webp").build());
        save(User.builder().authId(6L).salary(200000d).email("aktas@gmail.com").shiftId(1L).firstname("Aktaş").lastname("Sabancı").address("Halkali Caddesi No.198/200 Küçükçekmece İstanbul").password("123").companyEmail("akt.akt@ikolay.com").phone("5321333667").role(ERole.MANAGER).companyId(1L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/22.webp").build());
        save(User.builder().authId(7L).salary(200000d).email("bilge@gmail.com").shiftId(4L).firstname("Bilge").lastname("Adam").address("Selçuklu Mah. Kardelen Sok. No:27 Çankaya Ankara").password("123").companyEmail("bilg.bilg@ikolay.com").phone("5321234567").role(ERole.MANAGER).companyId(2L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/3.webp").build());
        save(User.builder().authId(8L).salary(200000d).email("ertan@gmail.com").shiftId(5L).firstname("Ertan").lastname("Tokinan").address("Göztepe Cad. Ergenekon Sk. No:12 Kadıköy İstanbul").password("123").companyEmail("ert.ert@ikolay.com").phone("5432345678").role(ERole.MANAGER).companyId(3L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/25.webp").build());
        save(User.builder().authId(9L).salary(200000d).email("ecem@gmail.com").shiftId(6L).firstname("Ecem").lastname("Gülnihal").address("Fatih Mah. Çınar Cad. No:9 Osmangazi Bursa").password("123").companyEmail("ecem.ecem@ikolay.com").phone("5543456789").role(ERole.MANAGER).companyId(4L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/6.webp").build());
        save(User.builder().authId(10L).salary(200000d).email("nihal@gmail.com").shiftId(7L).firstname("Nihal").lastname("Martlı").address("Karadeniz Cad. Zambak Sk. No:3 Samsun Samsun").password("123").companyEmail("nhl.nhl@ikolay.com").phone("5654567890").role(ERole.MANAGER).companyId(5L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/18.webp").build());
        save(User.builder().authId(11L).salary(200000d).email("kadriye@gmail.com").shiftId(8L).firstname("Kadriye").lastname("Gülnihal").address("Akasya Sok. Meşe Mah. No:8 Nilüfer Bursa").password("123").companyEmail("kdr.kdr@ikolay.com").phone("5765678901").role(ERole.MANAGER).companyId(6L).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/17.webp").build());
        save(User.builder().authId(12L).email("emrsfa@gmail.com").firstname("Emre").lastname("Sefa").password("123").companyEmail("emrsfa@gmail.com").phone("5320436761").role(ERole.VISITOR).status(EStatus.ACTIVE).photoUrl("https://mdbcdn.b-cdn.net/img/new/avatars/23.webp").build());
    }

    public List<FindAllCompanyEmployeesResponseDto> personelList(Long companyId) {
        return IUserMapper.INSTANCE.toListFindAllCompanyEmployeesResponseDto(userRepository.findByCompanyIdAndRoleOrderByFirstname(companyId, ERole.EMPLOYEE));
    }

    public Long findIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        return user.get().getId();
    }

    public List<AllConfirmationInfoResponseDto> findAllPendingManagers() {
        List<User> pendingManagers = userRepository.findAllByStatusAndRole(EStatus.PENDING, ERole.MANAGER);
        List<Long> compIds = pendingManagers.stream().map(x -> x.getCompanyId()).toList();
        List<ConfirmationInfoResponseDto> list = companyManager.companyInfoForConfirmation(compIds).getBody();
        List<AllConfirmationInfoResponseDto> neededInfo = pendingManagers.stream().map(manager -> {
            AllConfirmationInfoResponseDto returnInfo = IUserMapper.INSTANCE.toAllConfirmationResponseDto(manager);
            ConfirmationInfoResponseDto first = list.stream().filter(company -> company.getId() == manager.getCompanyId()).findFirst().get();
            returnInfo.setCompanyName(first.getCompanyName());
            returnInfo.setTaxNo(first.getTaxNo());
            return returnInfo;
        }).toList();

        return neededInfo;
    }

    public Boolean setShift(AddShiftToEmployeeRequestDto dto) {

        Long shiftId = shiftService.findIdByShiftNameAndCompanyId(dto.getShiftName(), dto.getCompanyId());
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        user.get().setShiftId(shiftId);
        update(user.get());

        return true;

    }

    public UserInformationResponseDto updateUser(UpdateUserRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByIdAndEmail(dto.getId(), dto.getEmail());
        if(optionalUser.isEmpty() && userRepository.existsByEmail(dto.getEmail()))
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Email adresi zaten mevcut.");
        else if (optionalUser.isEmpty()) {
            Optional<User> user = findById(dto.getId());
            dto.setAuthId(user.get().getAuthId());
            user.get().setFirstname(dto.getFirstname());
            user.get().setLastname(dto.getLastname());
            user.get().setEmail(dto.getEmail());
            user.get().setPhone(dto.getPhone());
            user.get().setAddress(dto.getAddress());
            user.get().setPhotoUrl(dto.getPhotoUrl());
            authManager.updateAuthInfo(dto);
            return IUserMapper.INSTANCE.toUserInformationResponseDto(update(user.get()));
        }
        else {
            dto.setAuthId(optionalUser.get().getAuthId());
            optionalUser.get().setFirstname(dto.getFirstname());
            optionalUser.get().setLastname(dto.getLastname());
            optionalUser.get().setPhone(dto.getPhone());
            optionalUser.get().setAddress(dto.getAddress());
            optionalUser.get().setPhotoUrl(dto.getPhotoUrl());
            authManager.updateAuthInfo(dto);
            return IUserMapper.INSTANCE.toUserInformationResponseDto(update(optionalUser.get()));
        }
    }

    public GetUserFirstnameAndLastnameResponseDto getFirstAndLastnameWithId(Long id) {
        Optional<User> user = findById(id);
        if (user.isEmpty())
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen veride problem mevcut.");
        return IUserMapper.INSTANCE.toGetUserFirstnameAndLastnameResponseDto(user.get());
    }

    public Double findTotalEmployeeSalary(Long companyId){
        return userRepository.findTotalEmployeeSalary(companyId);
    }

    public Boolean deleteEmployee(DeleteEmployeeRequestDto dto) {
        Optional<User> user = userRepository.findByIdAndCompanyId(dto.getId(),dto.getCompanyId());
        if(user.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Önyüzden gelen veriyi kontrol edin!");
        try {
            authManager.deleteEmployee(user.get().getAuthId());
        } catch (Exception e) {
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Data bütünlüğü bozulmuş ya da Önyüzden gelen bilgiler hatalı olabilir. Lütfen kontrol ediniz.");
        }
        deleteById(user.get().getId());
        return true;
    }

    public Boolean updateSalary(UpdateSalaryRequestDto dto) {
        Optional<User> user = findById(dto.getId());
        if(user.isEmpty())
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Önyüzden gelen veriyi kontrol edin.");
        user.get().setSalary(dto.getSalary());
        update(user.get());
        return true;
    }
}

