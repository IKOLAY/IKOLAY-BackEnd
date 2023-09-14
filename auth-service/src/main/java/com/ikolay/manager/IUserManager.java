package com.ikolay.manager;

import com.ikolay.dto.requests.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.ikolay.constant.EndPoints.REGISTER;

@FeignClient(url = "http://localhost:7073/api/v1/user",decode404 = true,name = "auth-user")
public interface IUserManager {

    @PostMapping(REGISTER)
    ResponseEntity<Long> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token);


}
