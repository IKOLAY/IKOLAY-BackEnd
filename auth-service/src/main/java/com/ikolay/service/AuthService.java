package com.ikolay.service;

import com.ikolay.repository.IAuthRepository;
import com.ikolay.repository.entity.Auth;
import com.ikolay.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;


    public AuthService(IAuthRepository authRepository) {
        super(authRepository);
        this.authRepository=authRepository;
    }
}
