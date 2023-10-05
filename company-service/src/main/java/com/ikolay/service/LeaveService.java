package com.ikolay.service;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.dto.requests.EmployeeLeaveRequestDto;
import com.ikolay.dto.response.GetCompanyLeavesResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ILeaveMapper;
import com.ikolay.repository.ILeaveRepository;
import com.ikolay.repository.entity.Leave;
import com.ikolay.repository.enums.ELeaveStatus;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LeaveService extends ServiceManager<Leave, Long> {
    private final ILeaveRepository leaveRepository;
    private final IUserManager userManager;

    public LeaveService(ILeaveRepository leaveRepository, IUserManager userManager) {
        super(leaveRepository);
        this.leaveRepository = leaveRepository;
        this.userManager = userManager;
    }


    public Leave createLeave(CreateLeaveRequestDto dto) {

        Long userId = null;
        if (dto.getEmail() != null) {
            try {
                userId = userManager.findIdByEmail(dto.getEmail()).getBody();
            } catch (Exception e) {
                throw new CompanyManagerException(ErrorType.BAD_REQUEST, "Geçerli bir mail adresi girdiğinizden emin olun.");
            }
            dto.setUserId(userId);
        }
        Leave leave = ILeaveMapper.INSTANCE.toLeave(dto);
        leave.setConfirmationDate(LocalDate.now());
        leave.setStatus(ELeaveStatus.ACCEPTED);
        return save(leave);
    }


    @PostConstruct
    public void defaultTatilBilgileri() {
        Optional<Leave> leave = leaveRepository.findById(1L);
        if (leave.isEmpty()){
            save(Leave.builder().leaveName("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-04-23")).build());
            save(Leave.builder().leaveName("19 Mayıs Gençlik ve Spor Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-05-19")).build());
            save(Leave.builder().leaveName("30 Ağustos Zafer Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-08-30")).build());
            save(Leave.builder().leaveName("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-04-23")).build());
            save(Leave.builder().leaveName("19 Mayıs Gençlik ve Spor Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-05-19")).build());
            save(Leave.builder().leaveName("30 Ağustos Zafer Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-08-30")).build());
        }

    }

    public List<GetCompanyLeavesResponseDto> findCompanyLeaves(Long companyId) {
        List<Leave> leaves = leaveRepository.findByCompanyIdAndUserIdIsNullAndStartingDateAfter(companyId, LocalDate.now());
        return ILeaveMapper.INSTANCE.toGetCompanyLeavesResponseDtos(leaves);
    }

    public Leave createEmployeeLeaveRequest(EmployeeLeaveRequestDto dto) {
        if(dto.getStartingDate().isBefore(LocalDate.now()))
            throw new CompanyManagerException(ErrorType.BAD_REQUEST,"İzin tarihi mevcut günden önce olamaz.");
        Leave leave = ILeaveMapper.INSTANCE.toLeave(dto);
        leave.setStatus(ELeaveStatus.PENDING);
        return save(leave);
    }

    public List<Leave> getEmployeesLeaveRequest(Long companyId, Long userId) {
        return leaveRepository.findByCompanyIdAndUserId(companyId, userId);
    }

    public List<Leave> findByCompanyIdAndStatus(Long companyId) {
        return leaveRepository.findByCompanyIdAndStatus(companyId, ELeaveStatus.PENDING);
    }

    public Leave confirmLeave(Long id) {
        Optional<Leave> leave = findById(id);
        if (leave.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzden gelen veriyi kontrol edin.");
        switch (leave.get().getStatus()) {
            case ACCEPTED, REJECTED:
                throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu izin hakkında daha önceden karar verilmiş!");
            case CANCELED:
                throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu izin, istek sahibi tarafından iptal edilmiştir!");
            default:
                leave.get().setStatus(ELeaveStatus.ACCEPTED);
                leave.get().setConfirmationDate(LocalDate.now());
                break;
        }
        return update(leave.get());
    }

    public Leave rejectLeave(Long id) {
        Optional<Leave> leave = findById(id);
        if (leave.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzden gelen veriyi kontrol edin.");
        switch (leave.get().getStatus()) {
            case ACCEPTED, REJECTED:
                throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu izin hakkında daha önceden karar verilmiş!");
            case CANCELED:
                throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu izin, istek sahibi tarafından iptal edilmiştir!");
            default:
                leave.get().setStatus(ELeaveStatus.REJECTED);
                leave.get().setConfirmationDate(LocalDate.now());
                break;
        }
        return update(leave.get());
    }
    public Leave cancelLeave(Long id) {
        Optional<Leave> leave = findById(id);
        if (leave.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzden gelen veriyi kontrol edin.");
        switch (leave.get().getStatus()) {
            case ACCEPTED, REJECTED:
                throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu izin hakkında daha önceden karar verilmiş!");
            default:
                leave.get().setStatus(ELeaveStatus.CANCELED);
                leave.get().setConfirmationDate(LocalDate.now());
                break;
        }
        return update(leave.get());
    }
}
