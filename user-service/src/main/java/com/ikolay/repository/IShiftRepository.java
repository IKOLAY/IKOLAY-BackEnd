package com.ikolay.repository;

import com.ikolay.repository.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> findByShiftName(String shiftName);

    Optional<Shift> findByShiftNameAndCompanyId(String shiftName, Long companyId);

    @Query("select s from Shift s where s.companyId=?1")
    List<Shift> findShiftsByCompanyId(Long companyId);

}
