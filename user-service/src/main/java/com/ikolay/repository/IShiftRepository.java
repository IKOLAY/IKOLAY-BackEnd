package com.ikolay.repository;

import com.ikolay.repository.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> findByShiftName(String shiftName);

    Optional<Shift> findByShiftNameAndCompanyId(String shiftName, Long companyId);

}
