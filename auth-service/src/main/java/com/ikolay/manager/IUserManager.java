package com.ikolay.manager;

import com.ikolay.dto.requests.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ikolay.constant.EndPoints.*;
@FeignClient(url = "${feign.user}",decode404 = true,name = "auth-user")
public interface IUserManager {

    @PostMapping(REGISTER)
    ResponseEntity<Boolean> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token);

    @DeleteMapping(DELETEFROMCONFIRMATION)
    ResponseEntity<Boolean> deleteFromConfirmation(@RequestParam Long authId);

    @PostMapping(CONFIRMATION) //test için eklendi düzenlenmeli.
    ResponseEntity<Boolean> confirmation(@RequestParam Long authId);

}
