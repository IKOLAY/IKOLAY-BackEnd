package com.ikolay.service;

import com.ikolay.dto.requests.AdvancePaymentRequestDto;
import com.ikolay.dto.requests.CreateAdvanceRequestDto;
import com.ikolay.dto.response.EmployeeAdvanceAddResponseDto;
import com.ikolay.exception.ErrorType;
import com.ikolay.exception.UserManagerException;
import com.ikolay.manager.ITransactionManager;
import com.ikolay.mapper.IAdvanceMapper;
import com.ikolay.repository.IAdvanceRepository;
import com.ikolay.repository.entity.Advance;
import com.ikolay.repository.entity.User;
import com.ikolay.repository.enums.EAdvanceStatus;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdvanceService extends ServiceManager<Advance, Long> {
    private final IAdvanceRepository advanceRepository;

    private final ITransactionManager transactionManager;

    private final UserService userService;

    public AdvanceService(IAdvanceRepository advanceRepository, ITransactionManager transactionManager, UserService userService) {
        super(advanceRepository);
        this.advanceRepository = advanceRepository;
        this.transactionManager = transactionManager;
        this.userService = userService;
    }

    public EmployeeAdvanceAddResponseDto createEmployeeAdvanceRequest(CreateAdvanceRequestDto dto) {
        Optional<User> user = userService.findById(dto.getUserId());
        Optional<Advance>  advance = null;
        if(user.isEmpty())
            throw new UserManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Lütfen önyüzden gelen veriyi kontrol edin.");
        if(dto.getAdvanceAmount()<0)
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Girilen değer negatif olamaz.");
        if(dto.getAdvanceAmount()>user.get().getSalary())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Talep edilen avans maaşınızda fazla olamaz.");
        LocalDate now = LocalDate.now();
        if (now.getDayOfMonth() >= 15) {
            advance= advanceRepository.findByConfirmationDateBetweenAndUserIdAndAdvanceStatusNot(
                    LocalDate.of(now.getYear(), now.getMonth(), 15),
                    LocalDate.of(now.plusMonths(1).getYear(), now.plusMonths(1).getMonth(), 14),user.get().getId(),EAdvanceStatus.REJECTED);

        } else {
            advance =  advanceRepository.findByConfirmationDateBetweenAndUserIdAndAdvanceStatusNot(
                    LocalDate.of(now.minusMonths(1).getYear(), now.minusMonths(1).getMonth(), 15),
                    LocalDate.of(now.getYear(), now.getMonth(), 14), user.get().getId(),EAdvanceStatus.REJECTED);

        }
        if(advance.isPresent())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Bu ay için yapılan avans talebiniz onaylandığı için yeni talep oluşturamazsınız!");
        advance= advanceRepository.findByUserIdAndAdvanceStatus(user.get().getId(),EAdvanceStatus.PENDING);
        if(advance.isPresent())
            throw new UserManagerException(ErrorType.BAD_REQUEST,"Henüz yanıtlanmamış avans talebiniz bulunmaktadır. Lütfen tekrar istek yollamadan önce yanıtlanmasını bekleyin.");


        Advance requestAdvance = IAdvanceMapper.INSTANCE.toAdvance(dto);
        requestAdvance.setAdvanceStatus(EAdvanceStatus.PENDING);
        requestAdvance.setDescription(user.get().getFirstname()+" "+user.get().getLastname()+" - "+requestAdvance.getDescription());
        EmployeeAdvanceAddResponseDto respDto = IAdvanceMapper.INSTANCE.toEmployeeAdvanceAddResponseDto(save(requestAdvance));
        respDto.setSystemMessage("Talebiniz Yönetici Kontrolüne Yollandı!!");
        return respDto;
    }

    public List<Advance> getEmployeesAdvances(Long companyId, Long userId) {
        return advanceRepository.findByCompanyIdAndUserId(companyId, userId);
    }

    public List<Advance> findByCompanyIdAndStatus(Long companyId) {
        return advanceRepository.findByCompanyIdAndAdvanceStatus(companyId, EAdvanceStatus.PENDING);
    }

    public Advance confirmAdvance(Long id) {
        Optional<Advance> advance = findById(id);
        if (advance.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND, "Ön yüzden sıkıntılı istek");
        if (!advance.get().getAdvanceStatus().equals(EAdvanceStatus.PENDING)) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED, "Daha önce karar verilmiş bir seçim");
        }
        Optional<User> user = userService.findById(advance.get().getUserId());
        if (user.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND,"Ön yüz hatası");
        }

        advance.get().setAdvanceStatus(EAdvanceStatus.ACCEPTED);
        advance.get().setConfirmationDate(LocalDate.now());
        AdvancePaymentRequestDto dto = IAdvanceMapper.INSTANCE.toAdvancePaymentRequestDto(advance.get());
        dto.setEmployeeId(advance.get().getUserId());
        dto.setTransactionAmount(advance.get().getAdvanceAmount());
        dto.setName(user.get().getFirstname()+" "+user.get().getLastname()+" - Avans Talebi - "+advance.get().getAdvanceAmount());
        transactionManager.addAdvancePayment(dto);
        return update(advance.get());
    }

    public Advance rejectAdvance(Long id) {
        Optional<Advance> advance = findById(id);
        if (advance.isEmpty())
            throw new UserManagerException(ErrorType.USER_NOT_FOUND, "Ön yüzden sıkıntılı istek");
        if (!advance.get().getAdvanceStatus().equals(EAdvanceStatus.PENDING)) {
            throw new UserManagerException(ErrorType.USER_NOT_CREATED, "Daha önce karar verilmiş bir seçim");
        } else {
            advance.get().setAdvanceStatus(EAdvanceStatus.REJECTED);
            advance.get().setConfirmationDate(LocalDate.now());
        }
        return update(advance.get());
    }

}
