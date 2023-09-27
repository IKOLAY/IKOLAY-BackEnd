package com.ikolay.service;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.dto.response.GetCompanyLeavesResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ILeaveMapper;
import com.ikolay.repository.ILeaveRepository;
import com.ikolay.repository.entity.Leave;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;


@Service
public class LeaveService extends ServiceManager<Leave,Long> {
    private final ILeaveRepository leaveRepository;
    private final IUserManager userManager;

    public LeaveService(ILeaveRepository leaveRepository, IUserManager userManager) {
        super(leaveRepository);
        this.leaveRepository = leaveRepository;
        this.userManager = userManager;
    }


    public Leave createLeave(CreateLeaveRequestDto dto) {

        Long userId = null;
        if(dto.getEmail()!=null){
            try {
                userId = userManager.findIdByEmail(dto.getEmail()).getBody();
            } catch (Exception e) {
                throw new CompanyManagerException(ErrorType.BAD_REQUEST,"Geçerli bir mail adresi girdiğinizden emin olun.");
            }
            dto.setUserId(userId);
        }

        return save(ILeaveMapper.INSTANCE.toLeave(dto));
    }



    @PostConstruct
    public void defaultTatilBilgileri(){
        save(Leave.builder().leaveName("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-04-23")).build());
        save(Leave.builder().leaveName("19 Mayıs Gençlik ve Spor Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-05-19")).build());
        save(Leave.builder().leaveName("30 Ağustos Zafer Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2023-08-30")).build());
        save(Leave.builder().leaveName("23 Nisan Ulusal Egemenlik ve Çocuk Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-04-23")).build());
        save(Leave.builder().leaveName("19 Mayıs Gençlik ve Spor Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-05-19")).build());
        save(Leave.builder().leaveName("30 Ağustos Zafer Bayramı").companyId(1L).duration(1).startingDate(LocalDate.parse("2024-08-30")).build());
    }

    public List<GetCompanyLeavesResponseDto> findCompanyLeaves(Long companyId) {
        List<Leave> leaves = leaveRepository.findByCompanyIdAndUserIdIsNullAndStartingDateAfter(companyId,LocalDate.now());
        return  ILeaveMapper.INSTANCE.toGetCompanyLeavesResponseDtos(leaves);
    }
}
