package com.ikolay.repository;

import com.ikolay.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);
}
