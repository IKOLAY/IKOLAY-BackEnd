package com.ikolay.repository;

import com.ikolay.dto.response.FindActiveMembershipCountResponseDto;
import com.ikolay.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {

    Boolean existsByTaxNo(String taxNo);

    List<Company> findByIdIn(List<Long> companyIds);

    List<Company> findByCompanyNameContaining(String searchValue);

    @Query("select new com.ikolay.dto.response.FindActiveMembershipCountResponseDto(c.membershipId as id, COUNT(c) as count) from Company c where c.membershipExpiration > ?1 group by c.membershipId")
    List<FindActiveMembershipCountResponseDto> findActiveMembershipCounts(LocalDate date);
}
