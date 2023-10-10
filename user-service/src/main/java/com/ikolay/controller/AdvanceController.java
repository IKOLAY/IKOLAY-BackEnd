package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;
import com.ikolay.dto.requests.CreateAdvanceRequestDto;
import com.ikolay.dto.response.EmployeeAdvanceAddResponseDto;
import com.ikolay.repository.entity.Advance;
import com.ikolay.service.AdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADVANCE)
public class AdvanceController {

    private final AdvanceService advanceService;

    @PostMapping(ADVANCEREQUEST) //Employee sayfasında avans talebi oluşturulabilmesi için hazırlanmış metod.
    ResponseEntity<EmployeeAdvanceAddResponseDto> createAdvance(@RequestBody CreateAdvanceRequestDto dto){
        return ResponseEntity.ok(advanceService.createEmployeeAdvanceRequest(dto));
    }

    @GetMapping(GETMYALLREQUESTS) // Employee sayfasında çalışanın yapmış olduğu tüm avans taleplerinin görüntülenmesi için hazırlanmıştır.
    ResponseEntity<List<Advance>> getEmployeeAdvanceRequest(Long companyId,Long userId){
        return ResponseEntity.ok(advanceService.getEmployeesAdvances(companyId,userId));
    }
    @GetMapping(PENDINGREQUESTS) //Manager'ın firmasına ait avans taleplerini görüntüleyebilmesi için hazırlanmıştır.
    ResponseEntity<List<Advance>> getPendingAdvances(@PathVariable Long companyId){
        return ResponseEntity.ok(advanceService.findByCompanyIdAndStatus(companyId));
    }

    @GetMapping(CONFIRM) //Manager sayfası için gelen isteklere olumlu yanıt verilmesi için hazırlanmıştır.
    ResponseEntity<Advance> confirmAdvanceRequest(@PathVariable Long id){
        return ResponseEntity.ok(advanceService.confirmAdvance(id));
    }

    @GetMapping(REJECT) // Manager sayfası için gelen isteklere olumsuz yanıt verilmesi için hazırlanmıştır.
    ResponseEntity<Advance> rejectAdvanceRequest(@PathVariable Long id){
        return ResponseEntity.ok(advanceService.rejectAdvance(id));
    }

}
