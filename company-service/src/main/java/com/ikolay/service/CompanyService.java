package com.ikolay.service;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.requests.UpdateCompanyRequestDto;
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
        Optional<Company> company = companyRepository.findById(1L);
        if (company.isEmpty()) {
            save(Company.builder().companyName("DUMMY CORP.").logo("https://bcassetcdn.com/social/o5jw7zottz/preview.png").phone("+17343971529").address("29488 Brown Ct Garden City Michigan 48135 USA").about("Managed Analyzing Interface").taxNo("742735410").build());
            save(Company.builder().companyName("BİLGE ADAM").logo("https://cdn.cloudwises.com/ba-assets/genel/icon.svg").phone("+908502016000").address("Reşitpaşa Mah. Katar Cad. İTÜ Teknokent Arı 3 No:4 B3 Sarıyer / İSTANBUL").about("BilgeAdam Teknoloji; 1997’de İstanbul’da kurulmuş, global operasyonlarını İngiltere ve Avrupa üzerinden yaygınlaştıran bir yazılım şirketidir.").taxNo("534459629").build());
            save(Company.builder().companyName("TOKİNAN INC.").logo("https://bcassetcdn.com/social/chm5gip2m2/preview.png").phone("+12129951107").address("53 E 8th St Manhattan North Dakota 10003 USA").about("Visionary Zeroadministration Database").taxNo("133511473").build());
            save(Company.builder().companyName("GÜLNİHAL INC.").logo("https://bcassetcdn.com/social/h9qi7fxc4w/preview.png").phone("+18136851909").address("333 N Falkenburg Rd Tampa Florida 33619 USA").about("Realigned Background Firmware").taxNo("336107911").build());
            save(Company.builder().companyName("MARTLI INC.").logo("https://bcassetcdn.com/social/fg1coht6y0/preview.png").phone("+17342130017").address("170 Enterprise Dr Ann Arbor Michigan 48103 USA").about("Cloned Interactive Synergy").taxNo("070936704").build());
            save(Company.builder().companyName("ADANEDHEL INC.").logo("https://bcassetcdn.com/social/ybmnhv0fs6/preview.png").phone("+15128649911").address("2425 Williams Dr Georgetown Texas 78633 USA").about("Self-Enabling Contextually-Based Encoding").taxNo("067624320").build());
        }
    }

    public Company getCompanyInformation(Long id) {
        Optional<Company> company = findById(id);
        if (company.isEmpty())
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return company.get();
    }

    public Company updateCompany(UpdateCompanyRequestDto dto) {
        Optional<Company> optionalCompany = findById(dto.getId());
        if (optionalCompany.isEmpty())
            throw new CompanyManagerException(ErrorType.INTERNAL_ERROR_SERVER, "Ön yüzden gelen company id'sinde hata mevcut.");
        optionalCompany.get().setAbout(dto.getAbout());
        optionalCompany.get().setCompanyName(dto.getCompanyName());
        optionalCompany.get().setAddress(dto.getAddress());
        optionalCompany.get().setPhone(dto.getPhone());
        return update(optionalCompany.get());
    }

    public List<ConfirmationInfoResponseDto> companyInfoForConfirmation(List<Long> companyIds) {
        List<Company> companies = companyRepository.findByIdIn(companyIds);
        return ICompanyMapper.INSTANCE.toConfirmationResponseDtos(companies);
    }

    public List<GetTop5ForCompanyResponseDto> findByCompanyNameTop5() {
        Sort sort = Sort.by(Sort.Direction.fromString("ASC"), "companyName");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Company> companies = companyRepository.findAll(pageable);
        List<Company> companyList = companies.get().toList();

        return ICompanyMapper.INSTANCE.toGetTop5ForCompanyResponseDto(companyList);
    }

    public List<Company> findBySearchValue(String searchValue){
        if(searchValue.equals(""))
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        return companyRepository.findByCompanyNameContaining(searchValue.toUpperCase());
    }
}
