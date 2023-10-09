package com.ikolay.controller;


import com.ikolay.dto.requests.ChangeMyMembershipRequestDto;
import com.ikolay.dto.requests.CreateMembershipRequestDto;
import com.ikolay.repository.entity.Membership;
import com.ikolay.service.MembershipService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ikolay.constant.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/membership")
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping("/createmembership") //Adminin istediği gibi membership üretebilmesi için.
    public ResponseEntity<Membership> createMembership(@RequestBody CreateMembershipRequestDto dto){
        return ResponseEntity.ok(membershipService.create(dto));
    }

    @GetMapping("/getactivememberships")
    public ResponseEntity<List<Membership>> getActiveMemberships(){
        return ResponseEntity.ok(membershipService.getActiveMemberships());
    }

    @GetMapping("/setmembershipstatuspassive")
    public ResponseEntity<Boolean> setMembershipToPassive(Long id){
        return ResponseEntity.ok(membershipService.setMembershipToPassive(id));
    }

    @PostMapping("/setselectedmembership")
    public  ResponseEntity<Boolean> setSelectedMembership(@RequestBody ChangeMyMembershipRequestDto dto){
        return ResponseEntity.ok(membershipService.setCompanysMembership(dto));
    }



}
