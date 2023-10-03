package com.ikolay.repository;

import com.ikolay.dto.response.GetCompanyLeavesResponseDto;
import com.ikolay.repository.entity.Leave;
import com.ikolay.repository.enums.ELeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ILeaveRepository extends JpaRepository<Leave,Long> {


    List<Leave> findByCompanyIdAndUserIdIsNullAndStartingDateAfter(Long companyId, LocalDate startingDate);

    List<Leave> findByCompanyIdAndUserId(Long companyId, Long userId);

    List<Leave> findByCompanyIdAndStatus(Long companyId, ELeaveStatus eLeaveStatus);
}
