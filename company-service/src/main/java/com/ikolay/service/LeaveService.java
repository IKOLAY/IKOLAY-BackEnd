package com.ikolay.service;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.manager.IUserManager;
import com.ikolay.mapper.ILeaveMapper;
import com.ikolay.repository.ILeaveRepository;
import com.ikolay.repository.entity.Leave;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;


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
}
