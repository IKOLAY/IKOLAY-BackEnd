package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;
import static com.ikolay.constant.EndPoints.DELETE;

import com.ikolay.dto.requests.*;
import com.ikolay.dto.response.*;
import com.ikolay.repository.entity.User;
import com.ikolay.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @DeleteMapping(DELETEFROMCONFIRMATION) // Admin onay sayfasındaki red ihtimali için hazırlandı. Red durumunda user siliniyor. Istek auth serviceden geliyor (FEIGN)
    ResponseEntity<Boolean> deleteFromConfirmation(@RequestParam Long authId){
        userService.deleteByAuthId(authId);
        return ResponseEntity.ok(true);
    }

    @PostMapping(CONFIRMATION)  // Admin onay sayfasındaki onay ihtimali için hazırlandı. Istek auth serviceden geliyor (FEIGN)
    ResponseEntity<Boolean> confirmation(@RequestParam Long authId){
        userService.confirmUser(authId);
        return ResponseEntity.ok(true);
    }

    @GetMapping(GETUSERINFORMATION) //Önyüzde login sayfasından olumlu dönüş sonrası gelen token alıp, giriş yapan kullanıcı hakkındaki gerekli bilgileri döndürmek için hazırlandı.
    ResponseEntity<UserInformationResponseDto> getUserInformation(String token){
        return ResponseEntity.ok(userService.getUserInformation(token));
    }

    @GetMapping(PERSONELLIST) // Manager sayfasındaki şirket çalışanlarını getirmek için hazırlandı.
    ResponseEntity<List<FindAllCompanyEmployeesResponseDto>> personelList(Long companyId){
        return ResponseEntity.ok(userService.personelList(companyId));
    }

    @GetMapping(FINDIDBYEMAIL) // createLeave metodu için hazırlandı. Eğer mail var ise mail'e ait kullanıcı id'sini döndürüyor.
    ResponseEntity<Long> findIdByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.findIdByEmail(email));
    }

    @GetMapping(FINDALLPENDINGMANAGERS) //Admin sayfasındaki status pending ve role manager olan tüm kullanıcıları döndürmek için yazıldı.
    ResponseEntity<List<AllConfirmationInfoResponseDto>> findAllPendingManagers(){
        return ResponseEntity.ok(userService.findAllPendingManagers());
    }

    @PostMapping(SETSHIFTTOUSER) //user'a shift eklemek için gerekli (Muhtemelen Manager sayfasına eklenecek)
    ResponseEntity<Boolean> setShiftToUser(@RequestBody AddShiftToEmployeeRequestDto dto){
        return ResponseEntity.ok(userService.setShift(dto));
    }

    @PutMapping(UPDATE) //kullanıcı bilgileri güncellerken gerekli. Auth service ile bağlatısı da var (FEIGN)
    ResponseEntity<UserInformationResponseDto> updateUser(@RequestBody UpdateUserRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @GetMapping(GETFIRSTANDLASTNAMEWITHID)
    ResponseEntity<GetUserFirstnameAndLastnameResponseDto> getFirstAndLastnameWithId(@PathVariable Long id){
        return ResponseEntity.ok(userService.getFirstAndLastnameWithId(id));
    }

    @GetMapping("/monthlyemployeesalary/{companyId}")
    ResponseEntity<Double> findTotalEmployeeSalary(@PathVariable Long companyId){
        System.out.println(userService.findTotalEmployeeSalary(companyId));
        return ResponseEntity.ok(userService.findTotalEmployeeSalary(companyId));
    }

    @DeleteMapping(DELETE)
    ResponseEntity<Boolean> deleteEmployee(@RequestBody DeleteEmployeeRequestDto dto){
        return ResponseEntity.ok(userService.deleteEmployee(dto));
    }

    @PutMapping("/updatesalary")
    ResponseEntity<Boolean> updateSalary(@RequestBody @Valid UpdateSalaryRequestDto dto){
        return ResponseEntity.ok(userService.updateSalary(dto));
    }

}
