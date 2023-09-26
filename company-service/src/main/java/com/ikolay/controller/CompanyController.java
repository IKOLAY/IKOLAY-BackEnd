package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.ConfirmationInfoResponseDto;
import com.ikolay.repository.entity.Company;
import com.ikolay.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping(REGISTER) //auth>user>company kaydı yapılırken kullanılan metod.
    public ResponseEntity<Long> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(companyService.register(dto));
    }

    @PostMapping(FINDCOMPANY) // employee kaydı yapılırken mail adresi üretmekmek için kullanılan metod.
    public ResponseEntity<String> findCompanyNameById(@RequestParam Long id){
        return ResponseEntity.ok(companyService.findCompanyNameById(id));
    }

    @DeleteMapping("/delete") //Admin onay metodu red durumunda çalışması için yazıldı.
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        companyService.deleteById(id);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/companyinformation") // Manager için şirket sayfasındaki şirket bilgilerini düzenle isteği için hazırlandı.
    //Dönüş tipi Dto'ya çevrilebilir Personel sayfasındaki firma bilgileri ile ortak kullanım için. Login işleminde istek atılabilir.
    public ResponseEntity<Company> getCompanyInformation(Long id) {
        return ResponseEntity.ok(companyService.getCompanyInformation(id));
    }

    @PutMapping(UPDATE) // Manager tarafından güncellenen bilgileri database aktarmak için yapıldı.
    public ResponseEntity<Company> updateCompany(Company company) {
        return ResponseEntity.ok(companyService.updateCompany(company));
    }

    @PostMapping("/getcompanynameandtaxno") //Admin sayfası manager onayı için hazırlandı. Firmanın tax ve name'ini döndürüyor.
    public ResponseEntity<List<ConfirmationInfoResponseDto>> companyInfoForConfirmation(@RequestBody List<Long> companyIds){
        return ResponseEntity.ok(companyService.companyInfoForConfirmation(companyIds));
    }
}
