package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.exception.CompanyManagerException;
import com.ikolay.exception.ErrorType;
import com.ikolay.mapper.ICompanyMapper;
import com.ikolay.repository.ICompanyRepository;
import com.ikolay.repository.entity.Company;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company,Long> {
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
}
