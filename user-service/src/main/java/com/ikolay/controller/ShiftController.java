package com.ikolay.controller;

import com.ikolay.dto.requests.CreateShiftRequestDto;
import com.ikolay.dto.requests.RegisterRequestDto;
import com.ikolay.dto.response.AllConfirmationInfoResponseDto;
import com.ikolay.dto.response.FindAllCompanyEmployeesResponseDto;
import com.ikolay.dto.response.UserInformationResponseDto;
import com.ikolay.repository.entity.Shift;
import com.ikolay.service.ShiftService;
import com.ikolay.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SHIFT)
public class ShiftController {
    private final ShiftService shiftService;

    @PostMapping(ADD) //Yeni Vardiya eklerken gerekli olacak.
    ResponseEntity<Shift> createShift(@RequestBody CreateShiftRequestDto dto){
        return ResponseEntity.ok(shiftService.createShift(dto));
    }

    @GetMapping("/findshift/{id}") // Personel sayfasındaki vardiya bilgileri ihtiyacı için gerekli.
    @Operation(summary = "Personel sayfası için.")
    ResponseEntity<Shift> findShiftById(@PathVariable Long id){
        return ResponseEntity.ok(shiftService.findShiftById(id));
    }


}
