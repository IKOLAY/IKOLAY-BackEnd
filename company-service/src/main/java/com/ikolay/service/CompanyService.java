package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.ConfirmationInfoResponseDto;
import com.ikolay.dto.response.GetTop5ForCompanyResponseDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.mapper.ICompanyMapper;
import com.ikolay.repository.ICompanyRepository;
import com.ikolay.repository.entity.Company;
import com.ikolay.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, Long> {
    private final ICompanyRepository companyRepository;

    public CompanyService(ICompanyRepository companyRepository) {
        super(companyRepository);
        this.companyRepository = companyRepository;
    }

    public String findCompanyNameById(Long id) {
        Optional<Company> company = findById(id);
        if (company.isEmpty())
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return company.get().getCompanyName();
    }

    public Long register(RegisterRequestDto dto) {
        if (companyRepository.existsByTaxNo(dto.getTaxNo())) throw new CompanyManagerException(ErrorType.TAX_NO_ERROR);
        Company save = save(ICompanyMapper.INSTANCE.toCompany(dto));
        return save.getId();
    }

    @PostConstruct
    private void addDefaultCompany() {
        save(Company.builder().companyName("dummyCorp").logo("3.jpg").phone("4440444").address("somewhereonearth").about("weworkin").taxNo("123123").build());
        save(Company.builder().companyName("ikolay").logo("2.jpg").phone("4441444").address("somewhereonearth").about("kalpsizler").taxNo("3333").build());
        save(Company.builder().companyName("tokinan a.ş").logo("4.jpg").phone("4442444").address("letsgo").about("basliyoruuz").taxNo("44444").build());
        save(Company.builder().companyName("gülnihal a.ş").logo("5.jpg").phone("4443444").address("hi").about("seskes").taxNo("55555").build());
        save(Company.builder().companyName("martlı a.ş").logo("6.jpg").phone("4444444").address("guerillamountain").about("armed").taxNo("66666").build());
        save(Company.builder().companyName("adanedhel a.ş").logo("7.jpg").phone("4445444").address("middle-earth").about("rivendale").taxNo("88888").build());
    }

    public Company getCompanyInformation(Long id) {
        Optional<Company> company = findById(id);
        if (company.isEmpty())
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return company.get();
    }

    public Company updateCompany(Company company) {
        Optional<Company> optionalCompany = findById(company.getId());
        if (optionalCompany.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Ön yüzden gelen company id'sinde hata mevcut.");
        return update(company);
    }

    public List<ConfirmationInfoResponseDto> companyInfoForConfirmation(List<Long> companyIds) {
        List<Company> companies = companyRepository.findByIdIn(companyIds);
        return ICompanyMapper.INSTANCE.toConfirmationResponseDtos(companies);
    }

    public List<GetTop5ForCompanyResponseDto> findByCompanyNameTop5() {
        Sort sort= Sort.by(Sort.Direction.fromString("ASC"), "companyName");
        Pageable pageable = PageRequest.of(0,5,sort);
        Page<Company> companies = companyRepository.findAll(pageable);
        List<Company> companyList = companies.get().toList();

        return ICompanyMapper.INSTANCE.toGetTop5ForCompanyResponseDto(companyList);
    }
}
