package com.ikolay.config.security;


import com.ikolay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserId(Long id) throws UsernameNotFoundException{
        Optional<com.ikolay.repository.entity.User> user = userService.findById(id);
        if(user.isPresent()){

            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(user.get().getRole().toString()));

            return User.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .accountLocked(false)
                    .accountExpired(false)
                    .authorities(authorityList)
                    .build();
        }
        return null;
    }
}
