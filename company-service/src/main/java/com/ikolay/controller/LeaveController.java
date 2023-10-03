package com.ikolay.controller;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.dto.requests.EmployeeLeaveRequestDto;
import com.ikolay.dto.response.GetCompanyLeavesResponseDto;
import com.ikolay.repository.entity.Leave;
import com.ikolay.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(LEAVE)
public class LeaveController {
    private final LeaveService leaveService;

    @PostMapping(CREATE)
        //Manager sayfasındaki tatil ekleme işlemleri için hazırlandı. Manager eklediği için Accepted ve confirmDate içererek ekleniyor.
    ResponseEntity<Leave> createLeave(@RequestBody @Valid CreateLeaveRequestDto dto) {
        return ResponseEntity.ok(leaveService.createLeave(dto));
    }

    @GetMapping(FINDCOMPANYLEAVES)
    ResponseEntity<List<GetCompanyLeavesResponseDto>> findCompanyLeaves(Long companyId) {
        return ResponseEntity.ok(leaveService.findCompanyLeaves(companyId));
    }


    @PostMapping("/sendleaverequest") //Employee
    ResponseEntity<Leave> createEmployeeLeaveRequest(@RequestBody @Valid EmployeeLeaveRequestDto dto){
        return ResponseEntity.ok(leaveService.createEmployeeLeaveRequest(dto));
    }

    @GetMapping("/getmyleaverequests") //Employee sayfasındaki requestleri göstermek için hazırlandı.
    ResponseEntity<List<Leave>> getEmployeesLeaveRequests(Long companyId,Long userId){
        return ResponseEntity.ok(leaveService.getEmployeesLeaveRequest(companyId,userId));
    }

    @GetMapping("/getcompanyspendingleaverequest/{companyId}") // Manager sayfasındaki pending requestleri döndürmek için hazırlandı.
    ResponseEntity<List<Leave>> getPendingLeaveRequest(@PathVariable Long companyId){
        return ResponseEntity.ok(leaveService.findByCompanyIdAndStatus(companyId));
    }

    @GetMapping("/confirmleave/{id}")
    ResponseEntity<Leave> confirmLeaveRequest(@PathVariable Long id){
        return ResponseEntity.ok(leaveService.confirmLeave(id));
    }

    @GetMapping("/rejectleave/{id}")
    ResponseEntity<Leave> rejectLeaveRequest(@PathVariable Long id){
        return ResponseEntity.ok(leaveService.rejectLeave(id));
    }

    @GetMapping("/cancelleave/{id}")
    ResponseEntity<Leave> cancelLeaveRequest(@PathVariable Long id){
        return ResponseEntity.ok(leaveService.cancelLeave(id));
    }
}
