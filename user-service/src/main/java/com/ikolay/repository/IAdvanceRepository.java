package com.ikolay.repository;

import com.ikolay.repository.entity.Advance;
import com.ikolay.repository.enums.EAdvanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAdvanceRepository extends JpaRepository<Advance,Long> {
    List<Advance> findAdvancesByCompanyId(Long companyId);

    List<Advance> findByCompanyIdAndUserId(Long companyId, Long userId);

    List<Advance> findByCompanyIdAndAdvanceStatus(Long companyId, EAdvanceStatus eAdvanceStatus);

    Optional<Advance> findByConfirmationDateBetweenAndUserIdAndAdvanceStatus(LocalDate of, LocalDate of1, Long id, EAdvanceStatus eAdvanceStatus);

    Optional<Advance> findByConfirmationDateBetweenAndUserIdAndAdvanceStatusNot(LocalDate of, LocalDate of1, Long id, EAdvanceStatus eAdvanceStatus);

    @Query("select sum(a.advanceAmount) from Advance a where a.confirmationDate between ?1 and ?2 and a.companyId=?3 and a.advanceStatus=?4 group by a.companyId")
    Double findMonthlyTotalAdvance(LocalDate of, LocalDate of1, Long companyId, EAdvanceStatus eAdvanceStatus);

    Optional<Advance> findByUserIdAndAdvanceStatus(Long id, EAdvanceStatus eAdvanceStatus);
}
