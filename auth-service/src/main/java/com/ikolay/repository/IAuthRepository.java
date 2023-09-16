package com.ikolay.repository;

import com.ikolay.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {
    Boolean existsByEmail(String email);

    Optional<Auth> findOptionalByCompanyEmailAndPassword(String email, String password);
    Optional<Auth> findOptionalByIdAndActivationCode(Long id, String activationCode);

    List<Auth> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<Auth> findByEmail(String email);
}
