package com.ikolay.controller;

import com.ikolay.dto.requests.CreateLeaveRequestDto;
import com.ikolay.repository.entity.Leave;
import com.ikolay.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(LEAVE)
public class LeaveController {
    private final LeaveService leaveService;


    @GetMapping("/test")
    ResponseEntity<String> deneme(){
        return ResponseEntity.ok("Selam!");
    }


    @PostMapping("/test")
    ResponseEntity<Leave> createLeave(@RequestBody @Valid CreateLeaveRequestDto dto){
        return ResponseEntity.ok(leaveService.createLeave(dto));
    }

}
