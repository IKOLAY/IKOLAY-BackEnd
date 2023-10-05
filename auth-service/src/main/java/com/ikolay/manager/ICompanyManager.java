package com.ikolay.manager;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FeignClient(url = "${feign.company}",decode404 = true,name = "auth-company")
public interface ICompanyManager {

    @PostMapping(REGISTER)
    ResponseEntity<Long> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token);

    @PostMapping(FINDCOMPANY)
     ResponseEntity<String> findCompanyNameById(@RequestParam Long id);

    @DeleteMapping(DELETE)
    ResponseEntity<Boolean> deleteById(@RequestParam Long id);
}
