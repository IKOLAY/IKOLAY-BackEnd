package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.*;
import com.ikolay.dto.response.DoLoginResponseDto;
import com.ikolay.dto.response.RegisterResponseDto;
import com.ikolay.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;
    @PostMapping(REGISTER) // Kayıt olma işlemleri için hazırlandı. auth>user>company zincirinin ilk parçası.
    ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(LOGIN) // Giriş işlemleri için hazırlandı.
    public ResponseEntity<DoLoginResponseDto> login(@RequestBody DoLoginRequestDto dto){
        return ResponseEntity.ok(authService.doLogin(dto));
    }

    @GetMapping(ACTIVATION) //Atılan mail üzeirnden giden aktivasyon kodunu kullanarak accountu aktive etmek için hazırlandı.
    public ResponseEntity<RegisterResponseDto> activation(String token){
    return ResponseEntity.ok(authService.activation(token));
    }

    @PostMapping(APPROVE) //Admin sayfasından gelen hazır bilgiye göre onay/red işlemleri için hazırlandı.
    public ResponseEntity<RegisterResponseDto> decideRegisterRequest(@RequestBody AdminApproveRequestDto dto){
        return ResponseEntity.ok(authService.confirmation(dto));
    }

    @PostMapping(UPDATE) //user-service içindeki "user" güncellemelerinin buraya da taşınması için kullanıldı. (FEIGN)
    public ResponseEntity<Boolean> updateAuthInfo(@RequestBody UpdateUserRequestDto dto){
        return ResponseEntity.ok(authService.updateAuthInfo(dto));
    }

    @DeleteMapping(DELETE+"/{id}")
    ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.ok(authService.deleteEmployee(id));
    }
}
