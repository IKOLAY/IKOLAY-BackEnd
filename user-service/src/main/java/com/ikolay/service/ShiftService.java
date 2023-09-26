package com.ikolay.service;

import com.ikolay.dto.requests.CreateShiftRequestDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.manager.ICompanyManager;
import com.ikolay.mapper.IShiftMapper;
import com.ikolay.repository.IShiftRepository;
import com.ikolay.repository.entity.Shift;
import com.ikolay.utility.JwtTokenManager;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ShiftService extends ServiceManager<Shift, Long> {

    private final IShiftRepository shiftRepository;
    private final JwtTokenManager tokenManager;

    public ShiftService(IShiftRepository shiftRepository, JwtTokenManager tokenManager) {
        super(shiftRepository);
        this.shiftRepository = shiftRepository;
        this.tokenManager = tokenManager;

    }

    public Long findIdByShiftNameAndCompanyId(String shiftName,Long companyId){
        Optional<Shift> shift = shiftRepository.findByShiftNameAndCompanyId(shiftName,companyId);
        if (shift.isEmpty())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"ShiftError Olarak Eklenecek.");
        return shift.get().getId();
    }


    public Shift createShift(CreateShiftRequestDto dto) {
        Optional<Shift> shift = shiftRepository.findByShiftNameAndCompanyId(dto.getShiftName(),dto.getCompanyId());
        if (shift.isPresent())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Shift Already Exist hatası verilecek.");
        return save(IShiftMapper.INSTANCE.toShift(dto));
    }

    public Shift findShiftById(Long id) {
        Optional<Shift> shift = findById(id);
        if(shift.isEmpty())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Önyüzden gelen shift bilgisi kontrol edilmeli.");
        return shift.get();
    }



}

