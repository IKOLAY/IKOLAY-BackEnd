package com.ikolay.repository;

import com.ikolay.repository.entity.User;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);



    Optional<User> findByAuthId(Long authId); //test için eklendi düzenlenmeli.

    List<User> findByCompanyIdAndRole(Long companyId, ERole eRole);

    Optional<User> findByEmail(String email);

    List<User> findAllByStatusAndRole(EStatus eStatus, ERole eRole);
}
