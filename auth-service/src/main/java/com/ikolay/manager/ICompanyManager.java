package com.ikolay.manager;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:7073/api/v1/company",decode404 = true,name = "auth-company")
public interface ICompanyManager {

    @PostMapping(REGISTER)
    ResponseEntity<Long> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token);

    @PostMapping(FINDCOMPANY)
     ResponseEntity<String> findByCompanyName(@RequestParam Long id);

    @DeleteMapping("/delete") //düzenlenecek test için yazıldı.
    ResponseEntity<Boolean> deleteById(@RequestParam Long id);
}
