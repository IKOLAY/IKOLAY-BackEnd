package com.ikolay.repository;

import com.ikolay.repository.entity.User;
import com.ikolay.repository.enums.ERole;
import com.ikolay.repository.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    Optional<User> findByIdAndEmail(Long id, String email);
    Optional<User> findByCompanyIdAndEmail(Long companyId, String email);

    @Query("select sum(u.salary) from User u where u.companyId=?1 and u.role='EMPLOYEE'")
    Long findTotalEmployeeSalary(Long companyId);
}
