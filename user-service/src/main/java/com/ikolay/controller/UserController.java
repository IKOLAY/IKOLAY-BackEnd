package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.repository.entity.User;
import com.ikolay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {
    private final UserService userService;

    @PostMapping(REGISTER)
    ResponseEntity<Boolean> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.register(dto));
    }

    @DeleteMapping("/deletewithauthid") //test için yazıldı düzenlenecek.
    ResponseEntity<Boolean> deleteFromConfirmation(@RequestParam Long authId){
        userService.deleteByAuthId(authId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/confirmuser") //test için eklendi düzenlenmeli.
    ResponseEntity<Boolean> confirmation(@RequestParam Long authId){
        userService.confirmUser(authId);
        return ResponseEntity.ok(true);
    }

    @GetMapping(FINDALL)
    ResponseEntity<List<User>> findAllPersonel(){
        return ResponseEntity.ok(userService.findAll());
    }
}
