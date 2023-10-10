package com.ikolay.repository;

import com.ikolay.repository.entity.Advance;
import com.ikolay.repository.enums.EAdvanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdvanceRepository extends JpaRepository<Advance,Long> {
    List<Advance> findAdvancesByCompanyId(Long companyId);

    List<Advance> findByCompanyIdAndUserId(Long companyId, Long userId);

    List<Advance> findByCompanyIdAndAdvanceStatus(Long companyId, EAdvanceStatus eAdvanceStatus);
}
