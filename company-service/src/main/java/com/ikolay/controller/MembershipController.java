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
@RequestMapping(MEMBERSHIP)
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping(CREATE) //Adminin istediği gibi membership üretebilmesi için.
    public ResponseEntity<Membership> createMembership(@RequestBody CreateMembershipRequestDto dto){
        return ResponseEntity.ok(membershipService.create(dto));
    }

    @GetMapping(GETMEMBERSHIPS) // Kullanıcıya listelenecek metodlar
    public ResponseEntity<List<Membership>> getActiveMemberships(){
        return ResponseEntity.ok(membershipService.getActiveMemberships());
    }

    @GetMapping(SETPASSIVE) // Adminin sonlandırmak istediği membershipler için kullanacağı metod
    public ResponseEntity<Boolean> setMembershipToPassive(Long id){
        return ResponseEntity.ok(membershipService.setMembershipToPassive(id));
    }

    @PostMapping("/setselectedmembership") // Kullanıcıya seçimini eklemek için
    public  ResponseEntity<Boolean> setSelectedMembership(@RequestBody ChangeMyMembershipRequestDto dto){
        return ResponseEntity.ok(membershipService.setCompanysMembership(dto));
    }



}
