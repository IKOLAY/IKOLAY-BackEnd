package com.ikolay.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:7072/api/v1/user",decode404 = true,name = "company-user")
public interface IUserManager {

    @GetMapping("/finduseridbyemail/{email}")
    ResponseEntity<Long> findIdByEmail(@PathVariable String email);


}
