package com.ikolay.controller;

import static com.ikolay.constant.EndPoints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    @GetMapping
    public ResponseEntity<String> selam(){
        return ResponseEntity.ok("selam");
    }
}
