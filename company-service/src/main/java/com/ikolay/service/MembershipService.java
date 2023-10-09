package com.ikolay.service;

import com.ikolay.dto.requests.ChangeMyMembershipRequestDto;
import com.ikolay.dto.requests.CreateMembershipRequestDto;
import com.ikolay.dto.response.FindActiveMembershipCountResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.mapper.IMembershipMapper;
import com.ikolay.repository.IMembershipRepository;
import com.ikolay.repository.entity.Company;
import com.ikolay.repository.entity.FinancialTransaction;
import com.ikolay.repository.entity.Membership;
import com.ikolay.repository.enums.*;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MembershipService extends ServiceManager<Membership, Long> {
    private final IMembershipRepository membershipRepository;
    private final CompanyService companyService;

    private final TransactionService transactionService;

    public MembershipService(IMembershipRepository membershipRepository, CompanyService companyService, TransactionService transactionService) {
        super(membershipRepository);
        this.membershipRepository = membershipRepository;
        this.companyService = companyService;
        this.transactionService = transactionService;
    }


    public Membership create(CreateMembershipRequestDto dto) {
        Membership membership = IMembershipMapper.INSTANCE.toMembership(dto);
        membership.setStatus(EMembershipStatus.ACTIVE);
        membership.setActiveUserCount(0L);
        if (!membership.getCurrencyType().equals(ECurrencyType.TL))
            membership.setPrice(membership.getPrice() * dto.getCurrencyMultiplier());
        return save(membership);
    }


    public List<Membership> getActiveMemberships() {
        refreshActiveUserCounts();
        return membershipRepository.findByStatusOrderByPriceAsc(EMembershipStatus.ACTIVE);
    }

    public Boolean setMembershipToPassive(Long id) {
        Optional<Membership> membership = findById(id);
        if (membership.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzde hata var");
        if (membership.get().getStatus().equals(EMembershipStatus.PASSIVE))
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Bu paket zaten sonlandırılmıştır.");
        membership.get().setStatus(EMembershipStatus.PASSIVE);
        update(membership.get());
        return true;
    }

    private void refreshActiveUserCounts() {
        List<FindActiveMembershipCountResponseDto> activeUserCounts = companyService.getActiveUserCounts();
        List<Membership> list = membershipRepository.findAll();

        list.forEach(x->{
            Optional<FindActiveMembershipCountResponseDto> first = activeUserCounts.stream().filter(y -> y.getId().equals(x.getId())).findFirst();
            if(first.isEmpty()){
                x.setActiveUserCount(0L);
                update(x);
            } else {
                x.setActiveUserCount(first.get().getCount());
                update(x);
            }
        });
    }

    public Boolean setCompanysMembership(ChangeMyMembershipRequestDto dto) {
        Long extraDays = 0L;
        Optional<Membership> membership = findById(dto.getMembershipId());
        if (membership.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzden gelen veride hata mevcut.");
        if(membership.get().getStatus().equals(EMembershipStatus.PASSIVE))
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER,"Bu paket artık geçerli değil, Lütfen sayfayı yenileyiniz.");
        Optional<Company> company = companyService.findById(dto.getCompanyId());
        if (company.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Önyüzden gelen şirket verisinde hata mevcut.");
        company.get().setMembershipId(dto.getMembershipId());
        company.get().setMembershipStarted(LocalDate.now());
        if (company.get().getMembershipExpiration().isAfter(LocalDate.now()))
            extraDays = ChronoUnit.DAYS.between(LocalDate.now(), company.get().getMembershipExpiration());
        company.get().setMembershipExpiration(LocalDate.now().plusDays(membership.get().getMembershipDuration() + extraDays));
        companyService.update(company.get());
        transactionService.save(FinancialTransaction.builder()
                .companyId(company.get().getId())
                .name("IKolay Üyelik Ödemesi - " + membership.get().getName())
                .expenseType(EExpenseType.MANAGER)
                        .currencyMultiplier(membership.get().getCurrencyMultiplier())
                        .currencyType(membership.get().getCurrencyType())
                        .transactionDate(LocalDate.now())
                        .transactionAmount(membership.get().getPrice()*-1)
                        .isPaid(true)
                        .status(ETransactionStatus.ACCEPTED)
                        .confirmationDate(LocalDate.now())
                        .type(ETransactionType.OUTCOME)
                .build());
        return true;
    }


    @PostConstruct
    public void defaultMemberShips(){
        save(Membership.builder().name("Test").membershipDuration(30L).currencyType(ECurrencyType.TL).currencyMultiplier(1d).price(25000d).status(EMembershipStatus.ACTIVE).build());
        save(Membership.builder().name("Test2").membershipDuration(30L).currencyType(ECurrencyType.TL).currencyMultiplier(1d).price(30000d).status(EMembershipStatus.ACTIVE).build());
    }
}
