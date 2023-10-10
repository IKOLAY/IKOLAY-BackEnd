package com.ikolay.controller;

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
@RequestMapping("/advance")
public class AdvanceController {

    private final AdvanceService advanceService;

    @PostMapping("/sendadvancerequest")
    ResponseEntity<EmployeeAdvanceAddResponseDto> createAdvance(@RequestBody CreateAdvanceRequestDto dto){
        return ResponseEntity.ok(advanceService.createEmployeeAdvanceRequest(dto));
    }

    @GetMapping("/getoneemployeerequests")
    ResponseEntity<List<Advance>> getEmployeeAdvanceRequest(Long companyId,Long userId){
        return ResponseEntity.ok(advanceService.getEmployeesAdvances(companyId,userId));
    }
    @GetMapping("/getcompanypendingadvancerequest/{companyId}")
    ResponseEntity<List<Advance>> getPendingAdvances(@PathVariable Long companyId){
        return ResponseEntity.ok(advanceService.findByCompanyIdAndStatus(companyId));
    }

    @GetMapping("/confirmadvance/{id}")
    ResponseEntity<Advance> confirmAdvanceRequest(@PathVariable Long id){
        return ResponseEntity.ok(advanceService.confirmAdvance(id));
    }

    @GetMapping("/rejectadvance/{id}")
    ResponseEntity<Advance> rejectAdvanceRequest(@PathVariable Long id){
        return ResponseEntity.ok(advanceService.rejectAdvance(id));
    }

}
