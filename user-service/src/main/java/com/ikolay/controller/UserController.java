package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;

import com.ikolay.dto.requests.AddShiftToEmployeeRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.requests.UpdateUserRequestDto;
import com.ikolay.dto.response.AllConfirmationInfoResponseDto;
import com.ikolay.dto.response.FindAllCompanyEmployeesResponseDto;
import com.ikolay.dto.response.UpdateUserResponseDto;
import com.ikolay.dto.response.UserInformationResponseDto;
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

    @PostMapping(REGISTER) //Kullanıcı kaydının ara elemanı auth>user>company. Kayıt işlemi sırasıda gelen bilgileri User DB'sine de kayıt etmek için hazırlandı. (FEING)
    ResponseEntity<Boolean> register(@RequestBody RegisterRequestDto dto, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.register(dto));
    }

    @DeleteMapping("/deletewithauthid") // Admin onay sayfasındaki red ihtimali için hazırlandı. Red durumunda user siliniyor. Istek auth serviceden geliyor (FEIGN)
    ResponseEntity<Boolean> deleteFromConfirmation(@RequestParam Long authId){
        userService.deleteByAuthId(authId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/confirmuser")  // Admin onay sayfasındaki onay ihtimali için hazırlandı. Istek auth serviceden geliyor (FEIGN)
    ResponseEntity<Boolean> confirmation(@RequestParam Long authId){
        userService.confirmUser(authId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/loggeduser") //Önyüzde login sayfasından olumlu dönüş sonrası gelen token alıp, giriş yapan kullanıcı hakkındaki gerekli bilgileri döndürmek için hazırlandı.
    ResponseEntity<UserInformationResponseDto> getUserInformation(String token){
        return ResponseEntity.ok(userService.getUserInformation(token));
    }

    @GetMapping("/getallpersonelwithcompanyid") // Manager sayfasındaki şirket çalışanlarını getirmek için hazırlandı.
    ResponseEntity<List<FindAllCompanyEmployeesResponseDto>> personelList(Long companyId){
        return ResponseEntity.ok(userService.personelList(companyId));
    }

    @GetMapping("/finduseridbyemail/{email}") // createLeave metodu için hazırlandı. Eğer mail var ise mail'e ait kullanıcı id'sini döndürüyor.
    ResponseEntity<Long> findIdByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findIdByEmail(email));
    }

    @GetMapping("/pendingmanagers") //Admin sayfasındaki status pending ve role manager olan tüm kullanıcıları döndürmek için yazıldı.
    ResponseEntity<List<AllConfirmationInfoResponseDto>> findAllPendingManagers(){
        return ResponseEntity.ok(userService.findAllPendingManagers());
    }

    @PostMapping("/setshift") //user'a shift eklemek için gerekli (Muhtemelen Manager sayfasına eklenecek)
    ResponseEntity<Boolean> setShiftToUser(@RequestBody AddShiftToEmployeeRequestDto dto){
        return ResponseEntity.ok(userService.setShift(dto));
    }

    @PutMapping(UPDATE) //kullanıcı bilgileri güncellerken gerekli. Auth service ile bağlatısı da var (FEIGN)
    ResponseEntity<UpdateUserResponseDto> updateUser(UpdateUserRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }

}
