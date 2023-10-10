package com.ikolay.service;

import com.ikolay.dto.requests.CreateAdvanceRequestDto;
import com.ikolay.dto.response.EmployeeAdvanceAddResponseDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.mapper.IAdvanceMapper;
import com.ikolay.repository.IAdvanceRepository;
import com.ikolay.repository.entity.Advance;
import com.ikolay.repository.enums.EAdvanceStatus;
import com.ikolay.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvanceService extends ServiceManager<Advance,Long> {
    private IAdvanceRepository advanceRepository;

    public AdvanceService(IAdvanceRepository advanceRepository) {
        super(advanceRepository);
        this.advanceRepository = advanceRepository;
    }

    public EmployeeAdvanceAddResponseDto createEmployeeAdvanceRequest(CreateAdvanceRequestDto dto) {
        Advance advance = IAdvanceMapper.INSTANCE.toAdvance(dto);
        advance.setAdvanceStatus(EAdvanceStatus.PENDING);
        save(advance);
        return EmployeeAdvanceAddResponseDto.builder()
                .message("Talebiniz Yönetici Kontrolüne Yollandı!!")
                .build();
    }

    public List<Advance> getEmployeesAdvances(Long companyId,Long userId){
        return advanceRepository.findByCompanyIdAndUserId(companyId,userId);
    }

    public List<Advance> findByCompanyIdAndStatus(Long companyId){
        return advanceRepository.findByCompanyIdAndAdvanceStatus(companyId, EAdvanceStatus.PENDING);
    }

    public Advance confirmAdvance(Long id){
        Optional<Advance> advance = findById(id);
        if (advance.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Ön yüzden sıkıntılı istek");
        if (advance.get().getAdvanceStatus() == EAdvanceStatus.ACCEPTED || advance.get().getAdvanceStatus() == EAdvanceStatus.REJECTED){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED,"Daha önce karar verilmiş bir seçim");
        }else {
            advance.get().setAdvanceStatus(EAdvanceStatus.ACCEPTED);
        }
        return update(advance.get());
        }

    public Advance rejectAdvance(Long id){
        Optional<Advance> advance = findById(id);
        if (advance.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Ön yüzden sıkıntılı istek");
        if (advance.get().getAdvanceStatus() == EAdvanceStatus.ACCEPTED || advance.get().getAdvanceStatus() == EAdvanceStatus.REJECTED){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED,"Daha önce karar verilmiş bir seçim");
        }else {
            advance.get().setAdvanceStatus(EAdvanceStatus.REJECTED);
        }
        return update(advance.get());
    }

}
