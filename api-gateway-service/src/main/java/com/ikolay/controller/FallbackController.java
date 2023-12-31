package com.ikolay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/authservice")
    public ResponseEntity<String> authServiceFallback() {
        return ResponseEntity.ok("Auth service şu anda hizmet verememektedir.");
    }

    @GetMapping("/userservice")
    public ResponseEntity<String> userServiceFallback() {
        return ResponseEntity.ok("User service şu anda hizmet verememektedir.");
    }
    @GetMapping("/companyworksservice")
    public ResponseEntity<String> companyServiceFallback() {
        return ResponseEntity.ok("Company service şu anda hizmet verememektedir.");
    }

}
