package com.ikolay.controller;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
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

    @PostMapping("/create")
        //Manager sayfasındaki tatil ekleme işlemleri için hazırlandı.
    ResponseEntity<Leave> createLeave(@RequestBody @Valid CreateLeaveRequestDto dto) {
        return ResponseEntity.ok(leaveService.createLeave(dto));
    }

    @GetMapping("/getcompanyleaves")
    ResponseEntity<List<GetCompanyLeavesResponseDto>> findCompanyLeaves(Long companyId) {


        return ResponseEntity.ok(leaveService.findCompanyLeaves(companyId));

    }
}
