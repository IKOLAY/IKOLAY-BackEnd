package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.ConfirmationInfoResponseDto;
import com.ikolay.dto.response.GetTop5ForCompanyResponseDto;
import com.ikolay.repository.entity.Company;
import com.ikolay.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping(REGISTER)
    public ResponseEntity<Long> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(companyService.register(dto));
    }
    @PostMapping(FINDCOMPANY)
    public ResponseEntity<String> findCompanyNameById(@RequestParam Long id){
        return ResponseEntity.ok(companyService.findCompanyNameById(id));
    }

    @DeleteMapping("/delete") //düzenlenecek test için yazıldı.
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        companyService.deleteById(id);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/companyinformation")
    public ResponseEntity<Company> getCompanyInformation(Long id) {
        return ResponseEntity.ok(companyService.getCompanyInformation(id));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Company> updateCompany(Company company) {
        return ResponseEntity.ok(companyService.updateCompany(company));
    }

    @PostMapping("/getcompanynameandtaxno")
    public ResponseEntity<List<ConfirmationInfoResponseDto>> companyInfoForConfirmation(@RequestBody List<Long> companyIds){
        return ResponseEntity.ok(companyService.companyInfoForConfirmation(companyIds));
    }

    @GetMapping("/findbycompanynametopfive")
    public ResponseEntity<List<GetTop5ForCompanyResponseDto>> findByCompanyNameTop5(){
        return ResponseEntity.ok(companyService.findByCompanyNameTop5());
    }

}
