package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> findByCompanyName(@RequestParam Long id){
        return ResponseEntity.ok(companyService.findCompanyNameById(id));
    }

    @DeleteMapping("/delete") //düzenlenecek test için yazıldı.
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        companyService.deleteById(id);
        return ResponseEntity.ok(true);
    }
}
